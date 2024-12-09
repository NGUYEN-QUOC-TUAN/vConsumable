$(document).ready(function () {
    let currentPage = 0;
    let pageSize = 5;

    // INITIALIZE - HIDE UPDATE BUTTON, LOAD VENDORS
    $('#update').hide();
    $('#delete').hide();
    loadVendors(currentPage);

    // HANDLING PAGINATION BUTTONS
    $('.pagination').on('click', '.page-link', function (e) {
        e.preventDefault();
        const action = $(this).attr('id');
        if (action === 'btn-previous' && currentPage > 0) {
            loadVendors(--currentPage);
        } else if (action === 'btn-next') {
            loadVendors(++currentPage);
        } else if ($(this).hasClass('page-link-number')) {
            loadVendors($(this).data('page'));
        }
    });

    // PAGE SIZE CHANGE HANDLER
    $('#page-size-select').change(function () {
        pageSize = parseInt($(this).val());
        loadVendors(0); // RESET TO FIRST PAGE
    });


    // LOAD VENDORS FUNCTION
    function loadVendors(page = 0) {
        currentPage = page;
        $.ajax({
            url: '/api/v1/vendor/get-all',
            method: 'GET',
            data: {page, size: pageSize},
            success: function (response) {
                renderTable(response._embedded.vendorList, page);
                renderPagination(response.page);
                $('#totalRecords').text('Records: ' + response._embedded.vendorList.length + ' / ' + response.page.totalElements);
                // THỰC HIỆN CLICK sau 1 giây
                $('.link-vendor').click();
            }
        });
    }

    // RENDER VENDOR TABLE
    function renderTable(vendors, page) {
        let rows = vendors.map((vendor, index) => `
            <tr>
                <th scope="row">${index + 1 + (page * pageSize)}</th>
                <td>${vendor.code}</td>
                <td>${vendor.desc}</td>
                <td>${vendor.group}</td>
                <td>
                    <div class="row">
                        <div class="col-12">
                            <button class="btn btn-primary w-100" onclick="editVendor('${vendor.code}', '${vendor.group}')">Edit</button>
                        </div>
                    </div>
                </td>
            </tr>`).join('');
        $('#tbl-vendor tbody').html(rows);
    }

    // SAVE NEW VENDOR
    $('#save').click(function () {
        const vendor = getVendorFormData();
        sendVendorData('/api/v1/vendor/save', 'POST', vendor);
    });

    // UPDATE VENDOR
    $('#update').click(function () {
        const vendor = getVendorFormData();
        sendVendorData('/api/v1/vendor/update', 'PUT', vendor);
    });

    // DELETE BUTTON CLICK TO OPEN MODAL
    $('#delete').click(function () {
        const code = $('#vendor_code').val();
        const group = $('#vendor_group').val();

        // CHECK IF VENDOR IS SELECTED BEFORE SHOWING MODAL
        if (code === "" || group === "") {
            alert("Please select a vendor to delete.");
            return false;
        }

        // OPEN MODAL
        $('#deleteConfirmationModal').modal('show');
    });

    // DELETE CONFIRMATION HANDLER
    $('#confirmDelete').click(function () {
        const code = $('#vendor_code').val();
        const group = $('#vendor_group').val();

        // SEND DELETE REQUEST
        $.ajax({
            url: `/api/v1/vendor/delete/${code}/${group}`,
            method: 'DELETE',
            success: function () {
                alert('Vendor deleted successfully!');
                loadVendors(currentPage);
                $('#deleteConfirmationModal').modal('hide');
                clearForm();  // CLEAR FORM AFTER DELETION
            }
        });
    });

    // FETCH AND EDIT VENDOR DATA
    window.editVendor = function (code, group) {
        $.ajax({
            url: `/api/v1/vendor/get-one/${code}/${group}`,
            method: 'GET',
            success: function (vendor) {
                setVendorFormData(vendor);
                $('#save').hide();
                $('#delete').show();
                $('#update').show();
            }
        });
    };

    // FORM DATA HANDLING
    function getVendorFormData() {
        return {
            code: $('#vendor_code').val(),
            desc: $('#vendor_name').val(),
            group: $('#vendor_group').val()
        };
    }

    function setVendorFormData(vendor) {
        $('#vendor_code').val(vendor.code);
        $('#vendor_name').val(vendor.desc);
        $('#vendor_group').val(vendor.group);
    }

    // SEND VENDOR DATA (SAVE/UPDATE)
    function sendVendorData(url, method, vendor) {
        $.ajax({
            url,
            method,
            contentType: 'application/json',
            data: JSON.stringify(vendor),
            success: function () {
                alert('Vendor processed successfully!');
                loadVendors(currentPage);
                clearForm();
            }
        });
    }

    // CLEAR FORM FUNCTION
    function clearForm() {
        $('#update').hide();
        $('#delete').hide();
        $('#save').show();
        $('#vendor_code').val('');
        $('#vendor_name').val('');
        $('#vendor_group').val('PUR');
    }

    // RESET FORM BUTTON
    $('#reset').click(function () {
        loadVendors(0);
        clearForm();
    });
});
