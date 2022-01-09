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

$(function () {
    $('input[fieldtype="DATE_RANGE"]').daterangepicker({
        "autoUpdateInput": false,
        showDropdowns: true,
        autoApply : true
    });
    $('input[fieldtype="DATE_RANGE"]').on('apply.daterangepicker', function (ev, picker) {
        $(this).val(picker.startDate.format('YYYY-MM-DD') + ' To ' + picker.endDate.format('YYYY-MM-DD'));
    });
    $('input[fieldtype="DATE_RANGE"]').on('cancel.daterangepicker', function (ev, picker) {
        $(this).val('');
    });

    var startDateSelector = $('input[fieldtype="START_DATE"]');
    var startDateValue = $.trim(startDateSelector.val());

    if (startDateValue != '') {
        startDateSelector.val(moment(startDateValue).format('MMM DD YYYY'));
    }
    startDateSelector.daterangepicker({
        autoUpdateInput: false,
        showDropdowns: true,
        singleDatePicker: true,
        autoApply : true,
        locale: {
            format: 'MMM DD YYYY'
        }
    });
    startDateSelector.on('apply.daterangepicker', function (ev, picker) {
        $(this).val(picker.startDate.format('MMM DD YYYY'));
        var exactPropertyName = $(this).attr('name').replace("StartDate", "");

        makeDummyHidden(exactPropertyName)
    });

    var endDateSelector = $('input[fieldtype="END_DATE"]');
    var endDateValue = $.trim(endDateSelector.val());

    if (endDateValue != '') {
        endDateSelector.val(moment(endDateValue).format('MMM DD YYYY'));
    }
    endDateSelector.daterangepicker({
        autoUpdateInput: false,
        showDropdowns: true,
        singleDatePicker: true,
        autoApply : true,
        locale: {
            format: 'MMM DD YYYY'
        }
    });
    endDateSelector.on('apply.daterangepicker', function (ev, picker) {
        $(this).val(picker.startDate.format('MMM DD YYYY'));
        var exactPropertyName = $(this).attr('name').replace("EndDate", "");
        makeDummyHidden(exactPropertyName);
    });


    var dateSelector = $('input[fieldtype="DATE"]');
    dateSelector.daterangepicker({
        autoUpdateInput: false,
        showDropdowns: true,
        singleDatePicker: true,
        autoApply : true,
        locale: {
            format: 'MMM DD YYYY'
        }
    });
    dateSelector.on('apply.daterangepicker', function (ev, picker) {
        $(this).val(picker.startDate.format('MMM DD YYYY'));
    });

    var dateTimeSelector = $('input[fieldtype="DATE_TIME"]');
    dateTimeSelector.daterangepicker({
        autoUpdateInput: false,
        showDropdowns: true,
        singleDatePicker: true,
        timePicker: true,
        locale: {
            format: 'MMM DD YYYY hh:mm A'
        }
    });

    dateTimeSelector.on('apply.daterangepicker', function (ev, picker) {
        if ($(this).attr('name') == 'transactionEndDate') {
            var lastDateTime = picker.startDate.format('MMM DD YYYY') + ' 11:59 PM';
            $(this).val(lastDateTime);
        } else {
            $(this).val(picker.startDate.format('MMM DD YYYY hh:mm A'));
        }
    });
});
