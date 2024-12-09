$(document).ready(function () {
    let currentPage = 0;
    let pageSize = 5;

    // INITIALIZE - HIDE UPDATE BUTTON, LOAD VENDORS
    $('#update').hide();
    $('#delete').hide();
    loadDepartments(currentPage);

    // HANDLING PAGINATION BUTTONS
    $('.pagination').on('click', '.page-link', function (e) {
        e.preventDefault();
        const action = $(this).attr('id');
        if (action === 'btn-previous' && currentPage > 0) {
            loadDepartments(--currentPage);
        } else if (action === 'btn-next') {
            loadDepartments(++currentPage);
        } else if ($(this).hasClass('page-link-number')) {
            loadDepartments($(this).data('page'));
        }
    });

    // PAGE SIZE CHANGE HANDLER
    $('#page-size-select').change(function () {
        pageSize = parseInt($(this).val());
        loadDepartments(0); // RESET TO FIRST PAGE
    });


    // LOAD VENDORS FUNCTION
    function loadDepartments(page = 0) {
        currentPage = page;
        $.ajax({
            url: '/api/v1/department/get-all',
            method: 'GET',
            data: {page, size: pageSize},
            success: function (response) {
                renderTable(response._embedded.departmentList, page);
                renderPagination(response.page);
                $('#totalRecords').text('Records: ' + response._embedded.departmentList.length + ' / ' + response.page.totalElements);
                // THỰC HIỆN CLICK sau 1 giây
                $('.link-department').click();
            }
        });
    }

    // RENDER VENDOR TABLE
    function renderTable(departments, page) {
        let rows = departments.map((department, index) => `
            <tr>
                <th scope="row">${index + 1 + (page * pageSize)}</th>
                <td>${department.code}</td>
                <td>${department.desc}</td>
                <td>${department.group}</td>
                <td>
                    <div class="row">
                        <div class="col-12">
                            <button class="btn btn-primary w-100" onclick="editDepartment('${department.code}', '${department.group}')">Edit</button>
                        </div>
                    </div>
                </td>
            </tr>`).join('');
        $('#tbl-department tbody').html(rows);
    }

    // SAVE NEW VENDOR
    $('#save').click(function () {
        const department = getDepartmentFormData();
        sendDepartmentData('/api/v1/department/save', 'POST', department);
    });

    // UPDATE VENDOR
    $('#update').click(function () {
        const department = getDepartmentFormData();
        setDepartmentFormData('/api/v1/department/update', 'PUT', department);
    });

    // DELETE BUTTON CLICK TO OPEN MODAL
    $('#delete').click(function () {
        const code = $('#department_code').val();
        const group = $('#department_group').val();

        // CHECK IF VENDOR IS SELECTED BEFORE SHOWING MODAL
        if (code === "" || group === "") {
            alert("Please select a department to delete.");
            return false;
        }

        // OPEN MODAL
        $('#deleteConfirmationModal').modal('show');
    });

    // DELETE CONFIRMATION HANDLER
    $('#confirmDelete').click(function () {
        const code = $('#department_code').val();
        const group = $('#department_group').val();

        // SEND DELETE REQUEST
        $.ajax({
            url: `/api/v1/department/delete/${code}/${group}`,
            method: 'DELETE',
            success: function () {
                alert('Department deleted successfully!');
                loadDepartments(currentPage);
                $('#deleteConfirmationModal').modal('hide');
                clearForm();  // CLEAR FORM AFTER DELETION
            }
        });
    });

    // FETCH AND EDIT VENDOR DATA
    window.editDepartment = function (code, group) {
        $.ajax({
            url: `/api/v1/department/get-one/${code}/${group}`,
            method: 'GET',
            success: function (vendor) {
                console.log(vendor);
                setDepartmentFormData(vendor);
                $('#save').hide();
                $('#delete').show();
                $('#update').show();
            }
        });
    };

    // FORM DATA HANDLING
    function getDepartmentFormData() {
        return {
            code: $('#department_code').val(),
            desc: $('#department_name').val(),
            group: $('#department_group').val()
        };
    }

    function setDepartmentFormData(department) {
        $('#department_code').val(department.code);
        $('#department_name').val(department.desc);
        $('#department_group').val(department.group);
    }

    // SEND VENDOR DATA (SAVE/UPDATE)
    function sendDepartmentData(url, method, department) {
        $.ajax({
            url,
            method,
            contentType: 'application/json',
            data: JSON.stringify(department),
            success: function () {
                alert('Department processed successfully!');
                loadDepartments(currentPage);
                clearForm();
            }
        });
    }

    // CLEAR FORM FUNCTION
    function clearForm() {
        $('#update').hide();
        $('#delete').hide();
        $('#save').show();
        $('#department_code').val('');
        $('#department_name').val('');
        $('#department_group').val('PUR');
    }

    // RESET FORM BUTTON
    $('#reset').click(function () {
        loadDepartments(0);
        clearForm();
    });
});
