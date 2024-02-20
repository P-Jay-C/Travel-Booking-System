$('document').ready(function () {
        $('table #editButton').on('click', function (event) {
            event.preventDefault();
            var href = $(this).attr('href');
            $.get(href, function (category, status) {
                $('#idEdit').val(category.id);
                $('#nameEdit').val(category.name);
                $('#descriptionEdit').val(category.description);
                $('#codeEdit').val(category.code);
                $('#iconEdit').val(category.icon);
                $('#activatedEdit').prop('checked', category.activated);
                $('#deletedEdit').prop('checked', category.deleted);
            });
            $('#editModal').modal();
        });
    });