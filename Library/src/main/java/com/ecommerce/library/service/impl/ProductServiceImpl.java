package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    private final ImageUpload imageUpload;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDto> products() {
        return transferData(productRepository.getAllProduct());
    }

    @Override
    public List<ProductDto> allProduct() {
        List<Product> products = productRepository.findAll();
        return transferData(products);
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setSalePrice(productDto.getSalePrice());
            product.setCategory(productDto.getCategory());
            product.set_deleted(false);
            product.set_activated(true);

            product.setLocationCountry(productDto.getLocationCountry());
            product.setLocationCity(productDto.getLocationCity());
            product.setType(productDto.getAccommodationType());
            product.setCapacity(productDto.getCapacity());
            product.setAccommodationPrice(productDto.getAccommodationPrice());

            product.setCarType(productDto.getCarType());
            product.setCarRentalPricePerDay(productDto.getCarRentalPricePerDay());

            product.setDepartureCity(productDto.getDepartureCity());
            product.setDestinationCity(productDto.getDestinationCity());
            product.setFlightDate(productDto.getFlightDate());
            product.setFlightPrice(productDto.getFlightPrice());

            product.setHotelName(productDto.getHotelName());
            product.setHotelLocation(productDto.getHotelLocation());
            product.setRoomTypes(productDto.getRoomTypes());
            product.setHotelPrice(productDto.getHotelPrice());

    return productRepository.save(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());

            // Handle image update
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }

            // Common fields
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());

            // AccommodationOffer fields
            productUpdate.setLocationCountry(productDto.getLocationCountry());
            productUpdate.setLocationCity(productDto.getLocationCity());
            productUpdate.setAccommodationType(productDto.getAccommodationType());
            productUpdate.setCapacity(productDto.getCapacity());
            productUpdate.setAccommodationPrice(productDto.getAccommodationPrice());

            // CarRentalOffer fields
            productUpdate.setCarType(productDto.getCarType());
            productUpdate.setCarRentalPricePerDay(productDto.getCarRentalPricePerDay());

            // FlightOffer fields
            productUpdate.setDepartureCity(productDto.getDepartureCity());
            productUpdate.setDestinationCity(productDto.getDestinationCity());
            productUpdate.setFlightDate(productDto.getFlightDate());
            productUpdate.setFlightPrice(productDto.getFlightPrice());

            // HotelOffer fields
            productUpdate.setHotelName(productDto.getHotelName());
            productUpdate.setHotelLocation(productDto.getHotelLocation());
            productUpdate.setRoomTypes(productDto.getRoomTypes());
            productUpdate.setHotelPrice(productDto.getHotelPrice());

            return productRepository.save(productUpdate);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    @Override
    public void enableById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.set_activated(true);
            product.set_deleted(false);
            productRepository.save(product);
        }

    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.set_deleted(true);
            product.set_activated(false);
            productRepository.save(product);
        }

    }

    @Override
    public ProductDto getById(Long id) {
        ProductDto productDto = new ProductDto();
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(product.getImage());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());

            // Offer-specific fields for AccommodationOffer
            productDto.setLocationCountry(product.getLocationCountry());
            productDto.setLocationCity(product.getLocationCity());
            productDto.setAccommodationType(product.getType());
            productDto.setCapacity(product.getCapacity());
            productDto.setAccommodationPrice(product.getAccommodationPrice());

            // Offer-specific fields for CarRentalOffer
            productDto.setCarType(product.getCarType());
            productDto.setCarRentalPricePerDay(product.getCarRentalPricePerDay());

            // Offer-specific fields for FlightOffer
            productDto.setDepartureCity(product.getDepartureCity());
            productDto.setDestinationCity(product.getDestinationCity());
            productDto.setFlightDate(product.getFlightDate());
            productDto.setFlightPrice(product.getFlightPrice());

            // Offer-specific fields for HotelOffer
            productDto.setHotelName(product.getHotelName());
            productDto.setHotelLocation(product.getHotelLocation());
            productDto.setRoomTypes(product.getRoomTypes());
            productDto.setHotelPrice(product.getHotelPrice());
        }
        return productDto;
    }


    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<ProductDto> randomProduct() {
        return transferData(productRepository.randomProduct());
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<ProductDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    @Override
    public Page<ProductDto> getAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<ProductDto> productDtoLists = this.allProduct();
        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
    }

    @Override
    public Page<ProductDto> getAllProductsForCustomer(int pageNo) {
        return null;
    }

    @Override
    public List<ProductDto> findAllByCategory(String category) {
        return transferData(productRepository.findAllByCategory(category));
    }

    @Override
    public List<ProductDto> filterHighProducts() {
        return transferData(productRepository.filterHighProducts());
    }

    @Override
    public List<ProductDto> filterLowerProducts() {
        return transferData(productRepository.filterLowerProducts());
    }

    @Override
    public List<ProductDto> listViewProducts() {
        return transferData(productRepository.listViewProduct());
    }

    @Override
    public List<ProductDto> findByCategoryId(Long id) {
        return transferData(productRepository.getProductByCategoryId(id));
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        return transferData(productRepository.searchProducts(keyword));
    }

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());
            productDto.setCategory(product.getCategory());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());

            // Offer-specific fields for AccommodationOffer
            productDto.setLocationCountry(product.getLocationCountry());
            productDto.setLocationCity(product.getLocationCity());
            productDto.setAccommodationType(product.getType());
            productDto.setCapacity(product.getCapacity());
            productDto.setAccommodationPrice(product.getAccommodationPrice());

            // Offer-specific fields for CarRentalOffer
            productDto.setCarType(product.getCarType());
            productDto.setCarRentalPricePerDay(product.getCarRentalPricePerDay());

            // Offer-specific fields for FlightOffer
            productDto.setDepartureCity(product.getDepartureCity());
            productDto.setDestinationCity(product.getDestinationCity());
            productDto.setFlightDate(product.getFlightDate());
            productDto.setFlightPrice(product.getFlightPrice());

            // Offer-specific fields for HotelOffer
            productDto.setHotelName(product.getHotelName());
            productDto.setHotelLocation(product.getHotelLocation());
            productDto.setRoomTypes(product.getRoomTypes());
            productDto.setHotelPrice(product.getHotelPrice());

            productDtos.add(productDto);
        }
        return productDtos;
    }

}
