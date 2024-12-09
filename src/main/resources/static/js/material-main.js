$(document).ready(function () {
    let currentPage = 0;
    let totalPages = 0;
    let pageSize = 1;

    $('#save').hide();
    $('#cancel').hide();

    $('#add').on('click', function () {
        // HIỂN THỊ CÁC COMBOBOX KHI NHẤN NÚT "ADD"
        // HIDE INPUT:
        //clearForm();
        $('#item_type').hide();
        $('#item_currency').hide();
        $('#item_vendor').hide();
        $('#item_um').hide();

        // SHOW SELECT:
        $('#item_type_select').show();
        $('#item_currency_select').show();
        $('#item_vendor_select').show();
        $('#item_um_select').show();

        // SHOW BUTTON:
        $('#save').show();
        $('#cancel').show();
        $('#update').hide();
        $('#reset').hide();
        $('#delete').hide();
        $('#add').hide();

        clearForm();
        comboboxType();
        comboboxCurrency();
        comboboxVendor();
        comboboxUnit();
    });


    function comboboxType(code) {

        if (code != null) {
            $.ajax({
                url: '/api/v1/category/get-by-code/' + code,
                method: 'GET',
                success: function (response) {
                    // GET ID INPUT
                    $('#item_type').val(response.code + ' - ' + response.desc);
                },
                error: function (error) {
                    $('#item_type').val('');
                    console.error('Error get by code data combobox category:', error);
                }
            });
        } else {
            $.ajax({
                url: '/api/v1/category/get-full',
                method: 'GET',
                success: function (response) {
                    // GET ID COMBOBOX
                    const selectElement = $('#item_type_select');
                    selectElement.empty();

                    response.forEach(function (item) {
                        const optionText = item.code + ' - ' + item.desc;
                        const option = $('<option>').val(item.code).text(optionText);
                        selectElement.append(option);
                    });
                    selectElement.show();
                },
                error: function (error) {
                    console.error('Error get full data combobox category:', error);
                }
            });
        }
    }

    function comboboxCurrency(code) {

        if (code != null) {
            $.ajax({
                url: '/api/v1/currency/get-one/' + code,
                method: 'GET',
                success: function (response) {
                    // GET ID INPUT
                    $('#item_currency').val(response.desc);
                },
                error: function (error) {
                    $('#item_currency').val('');
                    console.error('Error get by code data combobox currency:', error);
                }
            });
        } else {
            $.ajax({
                url: '/api/v1/currency/get-full',
                method: 'GET',
                success: function (response) {
                    // GET ID COMBOBOX
                    const selectElement = $('#item_currency_select');
                    selectElement.empty();

                    response.forEach(function (item) {
                        const optionText = item.desc;
                        const option = $('<option>').val(item.code).text(optionText);
                        selectElement.append(option);
                    });
                    selectElement.show();
                },
                error: function (error) {
                    console.error('Error get full data combobox currency:', error);
                }
            });
        }

    }

    function comboboxVendor(code) {
        if (code != null) {
            $.ajax({
                url: '/api/v1/vendor/get-by-code/' + code,
                method: 'GET',
                success: function (response) {
                    // GET ID INPUT
                    $('#item_vendor').val(response.code + ' - ' + response.desc);
                },
                error: function (error) {
                    $('#item_vendor').val('');
                    console.error('Error get by code data combobox vendor:', error);
                }
            });
        } else {
            $.ajax({
                url: '/api/v1/vendor/get-full',
                method: 'GET',
                success: function (response) {
                    // GET ID COMBOBOX
                    const selectElement = $('#item_vendor_select');
                    selectElement.empty();

                    response.forEach(function (item) {
                        const optionText = item.code + ' - ' + item.desc;
                        const option = $('<option>').val(item.code).text(optionText);
                        selectElement.append(option);
                    });
                    selectElement.show();
                },
                error: function (error) {
                    console.error('Error get full data combobox vendor:', error);
                }
            });
        }
    }

    function comboboxUnit(code) {

        if (code != null) {
            $.ajax({
                url: '/api/v1/unit/get-by-code/' + code,
                method: 'GET',
                success: function (response) {
                    // GET ID INPUT
                    $('#item_um').val(response.desc);
                },
                error: function (error) {
                    $('#item_um').val('');
                    console.error('Error get by code data combobox unit:', error);
                }
            });
        } else {
            $.ajax({
                url: '/api/v1/unit/get-full',
                method: 'GET',
                success: function (response) {
                    // GET ID COMBOBOX
                    const selectElement = $('#item_um_select');
                    selectElement.empty();

                    response.forEach(function (item) {
                        const optionText = item.desc;
                        const option = $('<option>').val(item.code).text(optionText);
                        selectElement.append(option);
                    });
                    selectElement.show();
                },
                error: function (error) {
                    console.error('Error get full data combobox unit:', error);
                }
            });
        }

    }


    $('#cancel').on('click', function () {
        // SHOW INPUT
        $('#item_type').show();
        $('#item_currency').show();
        $('#item_vendor').show();
        $('#item_um').show();

        // HIDE BUTTON:
        $('#save').hide();
        $('#cancel').hide();

        // SHOW BUTTON:
        $('#update').show();
        $('#reset').show();
        $('#delete').show();
        $('#add').show();

        // HIDE SELECT
        $('#item_type_select').hide();
        $('#item_currency_select').hide();
        $('#item_vendor_select').hide();
        $('#item_um_select').hide();
        loadMaterials(0);
    })

    $('#save').on('click', function () {
        // HIỂN THỊ CÁC COMBOBOX KHI NHẤN NÚT "ADD"
        $('#item_type').show();
        $('#item_currency').show();
        $('#item_vendor').show();
        $('#item_um').show();
        $('#item_type_select').hide();
        $('#item_currency_select').hide();
        $('#item_vendor_select').hide();
        $('#item_um_select').hide();
        console.log(getMaterialToForm());
        alert('Click To Save');

    });

    function getMaterialToForm() {
        return {
            num: $('#item_num').val(),
            std: $('#item_standard').val(),
            cat: $('#item_type_select option:selected').val(),
            curr: $('#item_currency_select option:selected').val(),
            cost: $('#item_standard').val(),
            um: $('#item_um_select option:selected').val(),
            vendor: $('#item_vendor_select option:selected').val(),
            ordlvl: $('#item_ordlvl').val(),
            moq: 0.8,
            group: 'PUR'
        }
    }

    function clearForm() {
        $('#item_num').val('');
        $('#item_desc').val('');
        $('#item_cost').val('');
        $('#item_standard').val('');
        $('#item_ordlvl').val('');
    }

    // INITIALIZE - LOAD VENDORS
    loadMaterials(currentPage);

    // HANDLING PAGINATION BUTTONS
    $('.pagination').on('click', '.page-link', function (e) {
        e.preventDefault();
        const action = $(this).attr('id');
        if (action === 'btn-previous' && currentPage > 0) {
            loadMaterials(currentPage - 1);
        } else if (action === 'btn-next' && currentPage < totalPages - 1) {
            loadMaterials(currentPage + 1);
        } else if ($(this).hasClass('page-link-number')) {
            loadMaterials($(this).data('page'));
        }
    });

    // PAGE SIZE CHANGE HANDLER
    $('#page-size-select').change(function () {
        pageSize = parseInt($(this).val());
        loadMaterials(0); // RESET TO FIRST PAGE
    });

    // LOAD VENDORS FUNCTION
    function loadMaterials(page = 0) {
        $.ajax({
            url: '/api/v1/material/get-all',
            method: 'GET',
            data: {page, size: pageSize},
            success: function (response) {
                const materials = response._embedded.materialList;
                if (materials.length > 0) {
                    renderForm(materials[0]);
                }
                currentPage = response.page.number;
                totalPages = response.page.totalPages;

                $('#totalRecords').text('Records: ' + (currentPage + 1) + ' / ' + totalPages);

                renderPagination(response.page);
            }
        });
    }

    function renderForm(material) {
        $('#item_num').val(material.num);
        $('#item_desc').val(material.desc);
        $('#item_cost').val(material.cost);
        $('#item_standard').val(material.std);
        $('#item_ordlvl').val(material.ordlvl ? material.ordlvl : '');


        comboboxType(material.cat);
        comboboxCurrency(material.curr);
        comboboxVendor(material.vendor);
        comboboxUnit(material.um)

    }

    // HÀM RENDER PHÂN TRANG
    function renderPagination(pageData) {
        $('#btn-first').parent().toggleClass('disabled', currentPage === 0);
        $('#btn-previous').parent().toggleClass('disabled', currentPage === 0);
        $('#btn-next').parent().toggleClass('disabled', currentPage === totalPages - 1);
        $('#btn-last').parent().toggleClass('disabled', currentPage === totalPages - 1);
    }

    // NÚT ĐIỀU HƯỚNG PHÂN TRANG
    $('#btn-first').click(function (e) {
        e.preventDefault();
        if (currentPage > 0) {
            loadMaterials(0);
        }
    });

    $('#btn-previous').click(function (e) {
        e.preventDefault();
        if (currentPage > 0) {
            loadMaterials(currentPage - 1);
        }
    });

    $('#btn-next').click(function (e) {
        e.preventDefault();
        if (currentPage < totalPages - 1) {
            loadMaterials(currentPage + 1);
        }
    });

    $('#btn-last').click(function (e) {
        e.preventDefault();
        if (currentPage < totalPages - 1) {
            loadMaterials(totalPages - 1);
        }
    });

});
