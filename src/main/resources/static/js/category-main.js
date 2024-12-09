$(document).ready(function () {
    let currentPage = 0;
    let pageSize = 5;

    // INITIALIZE - HIDE UPDATE BUTTON, LOAD CATEGORIES
    $('#update').hide();
    $('#delete').hide();
    loadCategories(currentPage);

    // HANDLING PAGINATION BUTTONS
    $('.pagination').on('click', '.page-link', function (e) {
        e.preventDefault();
        const action = $(this).attr('id');
        if (action === 'btn-previous' && currentPage > 0) {
            loadCategories(--currentPage);
        } else if (action === 'btn-next') {
            loadCategories(++currentPage);
        } else if ($(this).hasClass('page-link-number')) {
            loadCategories($(this).data('page'));
        }
    });

    // PAGE SIZE CHANGE HANDLER
    $('#page-size-select').change(function () {
        pageSize = parseInt($(this).val());
        loadCategories(0); // RESET TO FIRST PAGE
    });

    // LOAD CATEGORIES FUNCTION
    function loadCategories(page = 0) {
        currentPage = page;
        $.ajax({
            url: '/api/v1/category/get-all',
            method: 'GET',
            data: {page, size: pageSize},
            success: function (response) {
                renderTable(response._embedded.categoryList, page);
                renderPagination(response.page);
                console.log(response._embedded.categoryList);
                $('#totalRecords').text('Records: ' + response._embedded.categoryList.length + ' / ' + response.page.totalElements);
                // THỰC HIỆN CLICK sau 1 giây
                $('.link-category').click();
            }
        });
    }

    // RENDER CATEGORY TABLE
    function renderTable(categories, page) {
        let rows = categories.map((category, index) => `
            <tr>
                <th scope="row">${index + 1 + (page * pageSize)}</th>
                <td>${category.code}</td>
                <td>${category.desc}</td>
                <td>${category.group}</td>
                <td>
                    <div class="row">
                        <div class="col-12">
                            <button class="btn btn-primary w-100" onclick="editCategory('${category.code}', '${category.group}')">Edit</button>
                        </div>
                    </div>
                </td>
            </tr>`).join('');
        $('#tbl-category tbody').html(rows);
    }


    // SAVE NEW CATEGORY
    $('#save').click(function () {
        const category = getCategoryFormData();
        sendCategoryData('/api/v1/category/save', 'POST', category);
    });

    // UPDATE CATEGORY
    $('#update').click(function () {
        const category = getCategoryFormData();
        sendCategoryData('/api/v1/category/update', 'PUT', category);
    });

    // DELETE BUTTON CLICK TO OPEN MODAL
    $('#delete').click(function () {

        const code = $('#category_code').val();
        const group = $('#category_group').val();

        // CHECK IF CATEGORY IS SELECTED BEFORE SHOWING MODAL
        if (code === "" || group === "") {
            alert("Please select a category to delete.");
            return false;
        }

        // OPEN MODAL
        $('#deleteConfirmationModal').modal('show');
    });

    // DELETE CONFIRMATION HANDLER
    $('#confirmDelete').click(function () {
        const code = $('#category_code').val();
        const group = $('#category_group').val();

        // SEND DELETE REQUEST
        $.ajax({
            url: `/api/v1/category/delete/${code}/${group}`,
            method: 'DELETE',
            success: function () {
                alert('Category deleted successfully!');
                loadCategories(currentPage);
                $('#deleteConfirmationModal').modal('hide');
                clearForm();  // CLEAR FORM AFTER DELETION
            }
        });
    });

    // FETCH AND EDIT CATEGORY DATA
    window.editCategory = function (code, group) {
        $.ajax({
            url: `/api/v1/category/get-one/${code}/${group}`,
            method: 'GET',
            success: function (category) {
                setCategoryFormData(category);
                $('#save').hide();
                $('#delete').show();
                $('#update').show();
            }
        });
    };

    // FORM DATA HANDLING
    function getCategoryFormData() {
        return {
            code: $('#category_code').val(),
            desc: $('#category_name').val(),
            group: $('#category_group').val()
        };
    }

    function setCategoryFormData(category) {
        $('#category_code').val(category.code);
        $('#category_name').val(category.desc);
        $('#category_group').val(category.group);
    }

    // SEND CATEGORY DATA (SAVE/UPDATE)
    function sendCategoryData(url, method, category) {
        $.ajax({
            url,
            method,
            contentType: 'application/json',
            data: JSON.stringify(category),
            success: function () {
                alert('Category processed successfully!');
                loadCategories(currentPage);
                clearForm();
            }
        });
    }

    // CLEAR FORM FUNCTION
    function clearForm() {
        $('#update').hide();
        $('#delete').hide();
        $('#save').show();
        $('#category_code').val('');
        $('#category_name').val('');
        $('#category_group').val('PUR');
    }

    // RESET FORM BUTTON
    $('#reset').click(function () {
        loadCategories(0);
        clearForm();
    });
});
