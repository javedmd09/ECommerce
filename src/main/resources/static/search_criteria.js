$('#productId').select2({
    width: '100%',
    allowClear: true,
    placeholder: 'Select ID',
    ajax: {
        url: '/admin/auto-search/get-products-by-id',
        dataType: 'json',
        method: 'POST',
        delay: 500,
        data: function (params) {
            return {
                productId: params.term, // search term
                page: params.page
            };
        },
        results: function (data) {
            return {
                results: $.map(data, function (item) {
                    return {
                        text: item.text,
                        id: item.id
                    }
                })
            };
        }
    }
});

$('#productName').select2({
    width: '100%',
    allowClear: true,
    placeholder: 'Select Product Name',
    ajax: {
        url: '/admin/auto-search/get-products-by-name',
        dataType: 'json',
        method: 'POST',
        delay: 250,
        data: function (params) {
            return {
                productName: params.term, // search term
                page: params.page
            };
        },
        results: function (data) {
            return {
                results: $.map(data, function (item) {
                    return {
                        text: item.text,
                        id: item.id
                    }
                })
            };
        }
    }
});
