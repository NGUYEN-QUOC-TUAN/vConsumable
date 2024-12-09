$(document).ready(function () {
    let currentPage = 0;
    let pageSize = 5;

    // INITIALIZE - HIDE UPDATE BUTTON, LOAD VENDORS
    $('#update').hide();
    $('#delete').hide();
    loadUnits(currentPage);

    // HANDLING PAGINATION BUTTONS
    $('.pagination').on('click', '.page-link', function (e) {
        e.preventDefault();
        const action = $(this).attr('id');
        if (action === 'btn-previous' && currentPage > 0) {
            loadUnits(--currentPage);
        } else if (action === 'btn-next') {
            loadUnits(++currentPage);
        } else if ($(this).hasClass('page-link-number')) {
            loadUnits($(this).data('page'));
        }
    });

    // PAGE SIZE CHANGE HANDLER
    $('#page-size-select').change(function () {
        pageSize = parseInt($(this).val());
        loadUnits(0); // RESET TO FIRST PAGE
    });


    // LOAD VENDORS FUNCTION
    function loadUnits(page = 0) {
        currentPage = page;
        $.ajax({
            url: '/api/v1/unit/get-all',
            method: 'GET',
            data: {page, size: pageSize},
            success: function (response) {
                renderTable(response._embedded.unitList, page);
                renderPagination(response.page);
                $('#totalRecords').text('Records: ' + response._embedded.unitList.length + ' / ' + response.page.totalElements);
                // THỰC HIỆN CLICK sau 1 giây
                $('.link-unit-of-measure').click();
            }
        });
    }

    // RENDER VENDOR TABLE
    function renderTable(units, page) {
        let rows = units.map((unit, index) => `
            <tr>
                <th scope="row">${index + 1 + (page * pageSize)}</th>
                <td>${unit.code}</td>
                <td>${unit.desc}</td>
                <td>
                    <div class="row">
                        <div class="col-12">
                            <button class="btn btn-primary w-100" onclick="editUnit('${unit.code}', '${unit.group}')">Edit</button>
                        </div>
                    </div>
                </td>
            </tr>`).join('');
        $('#tbl-unit-measure tbody').html(rows);
    }

    // SAVE NEW VENDOR
    $('#save').click(function () {
        const unit = getUnitFormData();
        sendUnitData('/api/v1/unit/save', 'POST', unit);
    });

    // UPDATE VENDOR
    $('#update').click(function () {
        const unit = getUnitFormData();
        sendUnitData('/api/v1/unit/update', 'PUT', unit);
    });

    // DELETE BUTTON CLICK TO OPEN MODAL
    $('#delete').click(function () {
        const code = $('#unit_code').val();

        // CHECK IF VENDOR IS SELECTED BEFORE SHOWING MODAL
        if (code === "") {
            alert("Please select a unit to delete.");
            return false;
        }

        // OPEN MODAL
        $('#deleteConfirmationModal').modal('show');
    });

    // DELETE CONFIRMATION HANDLER
    $('#confirmDelete').click(function () {
        const code = $('#unit_code').val();
        // SEND DELETE REQUEST
        $.ajax({
            url: `/api/v1/unit/delete/${code}`,
            method: 'DELETE',
            success: function () {
                alert('Unit deleted successfully!');
                loadUnits(currentPage);
                $('#deleteConfirmationModal').modal('hide');
                clearForm();  // CLEAR FORM AFTER DELETION
            }
        });
    });

    // FETCH AND EDIT VENDOR DATA
    window.editUnit = function (code) {
        $.ajax({
            url: `/api/v1/unit/get-one/${code}`,
            method: 'GET',
            success: function (unit) {
                setUnitFormData(unit);
                $('#save').hide();
                $('#delete').show();
                $('#update').show();
            }
        });
    };

    // FORM DATA HANDLING
    function getUnitFormData() {
        return {
            code: $('#unit_code').val(),
            desc: $('#unit_name').val(),
        };
    }

    function setUnitFormData(unit) {
        $('#unit_code').val(unit.code);
        $('#unit_name').val(unit.desc);
    }

    // SEND VENDOR DATA (SAVE/UPDATE)
    function sendUnitData(url, method, unit) {
        $.ajax({
            url,
            method,
            contentType: 'application/json',
            data: JSON.stringify(unit),
            success: function () {
                alert('Unit processed successfully!');
                loadUnits(currentPage);
                clearForm();
            }
        });
    }

    // CLEAR FORM FUNCTION
    function clearForm() {
        $('#update').hide();
        $('#delete').hide();
        $('#save').show();
        $('#unit_code').val('');
        $('#unit_name').val('');
    }

    // RESET FORM BUTTON
    $('#reset').click(function () {
        loadUnits(0);
        clearForm();
    });
});
