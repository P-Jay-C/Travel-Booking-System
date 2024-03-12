package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.model.UserSession;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import com.ecommerce.library.service.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final UserSessionService userSessionService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        ShoppingCart cart;
        String username;

        if (principal != null) {
            username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            cart = customer.getCart();
        } else {
            UserSession userSession = userSessionService.getUserSession(session.getId());
            if (userSession == null) {
                userSession = userSessionService.createGuestUserSession(session);
            }
            cart = userSession.getShoppingCart();
        }

        if (cart == null) {
            model.addAttribute("check", "Cart is empty or null");
        } else {
            model.addAttribute("grandTotal", cart.getTotalPrice());
            model.addAttribute("shoppingCart", cart);
            session.setAttribute("totalItems", cart.getTotalItems());
        }

        model.addAttribute("title", "Cart");
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {

        ProductDto productDto = productService.getById(id);
        String username;

        if (principal != null) {
            username = principal.getName();
        } else {
            UserSession userSession = userSessionService.getUserSession(session.getId());
            if (userSession == null) {
                userSession = userSessionService.createGuestUserSession(session);
            }
            username = userSession.getSessionId();
        }

        ShoppingCart shoppingCart = cartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        ShoppingCart shoppingCart;

        if (principal != null) {
            String username = principal.getName();
            shoppingCart = cartService.updateCart(productService.getById(id), quantity, username);
        } else {
            UserSession userSession = userSessionService.getUserSession(session.getId());
            if (userSession == null) {
                return "redirect:/cart";
            }
            shoppingCart = cartService.updateCart(productService.getById(id), quantity, userSession.getSessionId());
        }

        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        ShoppingCart shoppingCart;

        if (principal != null) {
            String username = principal.getName();
            shoppingCart = cartService.removeItemFromCart(productService.getById(id), username);
        } else {
            UserSession userSession = userSessionService.getUserSession(session.getId());
            if (userSession == null) {
                return "redirect:/cart";
            }
            shoppingCart = cartService.removeItemFromCart(productService.getById(id), userSession.getSessionId());
        }

        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";
    }
}
