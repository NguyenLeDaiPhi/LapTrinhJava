<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User Management - Admin Dashboard</title>
    <meta name="author" content="Your Name">
    <meta name="description" content="Admin dashboard for user management">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css">
    <link rel="stylesheet" href="https://unpkg.com/tailwindcss@2.2.19/dist/tailwind.min.css" />
    <link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.min.js"
        integrity="sha256-xKeoJ50pzbUGkpQxDYHD7o7hxe0LaOGeguUidbq6vis=" crossorigin="anonymous"></script>

    <style>
        .modal {
            transition: opacity 0.25s ease;
        }

        .modal-open {
            overflow: hidden;
        }
    </style>
</head>

<body class="bg-gray-800 font-sans leading-normal tracking-normal mt-12">

    <header>
        <nav aria-label="menu nav" class="bg-gray-800 pt-2 md:pt-1 pb-1 px-1 mt-0 h-auto fixed w-full z-20 top-0">
            <div class="flex flex-wrap items-center">
                <div class="flex flex-shrink md:w-1/3 justify-center md:justify-start text-white">
                    <a href="#" aria-label="Home">
                        <span class="text-xl pl-2"><i class="em em-grinning"></i></span>
                    </a>
                </div>

                <div class="flex flex-1 md:w-1/3 justify-center md:justify-start text-white px-2">
                    <span class="relative w-full">
                        <input aria-label="search" type="search" id="search" placeholder="Search"
                            class="w-full bg-gray-900 text-white transition border border-transparent focus:outline-none focus:border-gray-400 rounded py-3 px-2 pl-10 appearance-none leading-normal">
                        <div class="absolute search-icon" style="top: 1rem; left: .8rem;">
                            <svg class="fill-current pointer-events-none text-white w-4 h-4"
                                xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                                <path
                                    d="M12.9 14.32a8 8 0 1 1 1.41-1.41l5.35 5.33-1.42 1.42-5.33-5.34zM8 14A6 6 0 1 0 8 2a6 6 0 0 0 0 12z" />
                            </svg>
                        </div>
                    </span>
                </div>

                <div class="flex w-full pt-2 content-center justify-between md:w-1/3 md:justify-end">
                    <ul class="list-reset flex justify-between flex-1 md:flex-none items-center">
                        <li class="flex-1 md:flex-none md:mr-3">
                            <a class="inline-block py-2 px-4 text-white no-underline" href="admin_dashboard.php">Phim</a>
                        </li>
                        <li class="flex-1 md:flex-none md:mr-3">
                            <a class="inline-block text-white no-underline hover:text-gray-200 hover:text-underline py-2 px-4"
                                href="admin_users.php">Người dùng</a>
                        </li>
                        <li class="flex-1 md:flex-none md:mr-3">
                            <div class="relative inline-block">
                                <button onclick="toggleDD('myDropdown')" class="drop-button text-white py-2 px-2">
                                    <span class="pr-2"><i class="em em-robot_face"></i></span> Admin <svg
                                        class="h-3 fill-current inline" xmlns="http://www.w3.org/2000/svg"
                                        viewBox="0 0 20 20">
                                        <path
                                            d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
                                    </svg></button>
                                <div id="myDropdown"
                                    class="dropdownlist absolute bg-gray-800 text-white right-0 mt-3 p-3 overflow-auto z-30 invisible">
                                    <input type="text" class="drop-search p-2 text-gray-600" placeholder="Search.."
                                        id="myInput" onkeyup="filterDD('myDropdown','myInput')">
                                    <a href="#" class="p-2 hover:bg-gray-800 text-white text-sm no-underline hover:no-underline block"><i
                                            class="fa fa-user fa-fw"></i> Profile</a>
                                    <a href="#" class="p-2 hover:bg-gray-800 text-white text-sm no-underline hover:no-underline block"><i
                                            class="fa fa-cog fa-fw"></i> Settings</a>
                                    <div class="border border-gray-800"></div>
                                    <a href="logout_process.php"
                                        class="p-2 hover:bg-gray-800 text-white text-sm no-underline hover:no-underline block"><i
                                            class="fas fa-sign-out-alt fa-fw"></i> Log Out</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>


    <main>
        <div class="flex flex-col md:flex-row">
            <nav aria-label="alternative nav">
                <div class="bg-gray-800 shadow-xl h-20 fixed bottom-0 mt-12 md:relative md:h-screen z-10 w-full md:w-48 content-center">
                    <div class="md:mt-12 md:w-48 md:fixed md:left-0 md:top-0 content-center md:content-start text-left justify-between">
                        <ul class="list-reset flex flex-row md:flex-col pt-3 md:py-3 px-1 md:px-2 text-center md:text-left">
                            <li class="mr-3 flex-1">
                                <a href="admin_dashboard.php"
                                    class="block py-1 md:py-3 pl-1 align-middle text-white no-underline hover:text-white border-b-2 border-gray-800 hover:border-pink-500">
                                    <i class="fas fa-film pr-0 md:pr-3"></i><span
                                        class="pb-1 md:pb-0 text-xs md:text-base text-gray-400 md:text-gray-200 block md:inline-block">Quản lý Phim</span>
                                </a>
                            </li>
                            <li class="mr-3 flex-1">
                                <a href="admin_users.php"
                                    class="block py-1 md:py-3 pl-1 align-middle text-white no-underline hover:text-white border-b-2 border-blue-600">
                                    <i class="fas fa-users pr-0 md:pr-3 text-blue-600"></i><span
                                        class="pb-1 md:pb-0 text-xs md:text-base text-white md:text-white block md:inline-block">Quản lý Người dùng</span>
                                </a>
                            </li>
                            <li class="mr-3 flex-1">
                                <a href="#"
                                    class="block py-1 md:py-3 pl-1 align-middle text-white no-underline hover:text-white border-b-2 border-gray-800 hover:border-purple-500">
                                    <i class="fas fa-chart-area pr-0 md:pr-3"></i><span
                                        class="pb-1 md:pb-0 text-xs md:text-base text-gray-400 md:text-gray-200 block md:inline-block">Thống kê</span>
                                </a>
                            </li>
                            <li class="mr-3 flex-1">
                                <a href="#"
                                    class="block py-1 md:py-3 pl-0 md:pl-1 align-middle text-white no-underline hover:text-white border-b-2 border-gray-800 hover:border-red-500">
                                    <i class="fa fa-wallet pr-0 md:pr-3"></i><span
                                        class="pb-1 md:pb-0 text-xs md:text-base text-gray-400 md:text-gray-200 block md:inline-block">Đơn hàng</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <section>
                <div id="main" class="main-content flex-1 bg-gray-100 mt-12 md:mt-2 pb-24 md:pb-5">

                    <div class="bg-gray-800 pt-3">
                        <div class="rounded-tl-3xl bg-gradient-to-r from-blue-900 to-gray-800 p-4 shadow text-2xl text-white">
                            <h1 class="font-bold pl-2">Quản lý Người Dùng</h1>
                        </div>
                    </div>

                    <div class="w-full mt-6 bg-white">
                        <div class="p-5">
                            <button id="addUserBtn"
                                class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mb-4">Thêm
                                Người dùng</button>
                            <table class="table-auto w-full">
                                <thead>
                                    <tr>
                                        <th class="px-4 py-2">ID</th>
                                        <th class="px-4 py-2">Tên</th>
                                        <th class="px-4 py-2">Email</th>
                                        <th class="px-4 py-2">Vai trò</th>
                                        <th class="px-4 py-2">Hành động</th>
                                    </tr>
                                </thead>
                                <tbody id="userTableBody">
                                    <!-- User data will be loaded here -->
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div id="addUserModal" class="modal fixed z-10 inset-0 overflow-y-auto hidden"
                        aria-labelledby="modal-title" role="dialog" aria-modal="true">
                        <div class="flex items-center justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                            <div class="fixed inset-0 transition-opacity" aria-hidden="true">
                                <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
                            </div>
                            <span class="hidden sm:inline-block sm:align-middle sm:h-screen"
                                aria-hidden="true">&#8203;</span>
                            <div
                                class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                    <h3 class="text-lg font-bold leading-6 text-gray-900" id="modal-title">Thêm Người Dùng
                                    </h3>
                                    <form id="addUserForm" action="add_user_process.php" method="POST">
                                        <div class="mb-4">
                                            <label for="name" class="block text-gray-700 text-sm font-bold mb-2">Tên:</label>
                                            <input type="text" id="name" name="name"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                        </div>
                                        <div class="mb-4">
                                            <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Email:</label>
                                            <input type="email" id="email" name="email"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                        </div>
                                        <div class="mb-4">
                                            <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Mật khẩu:</label>
                                            <input type="password" id="password" name="password"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                        </div>
                                        <div class="mb-4">
                                            <label for="role" class="block text-gray-700 text-sm font-bold mb-2">Vai trò:</label>
                                            <select id="role" name="role"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                                <option value="user">User</option>
                                                <option value="admin">Admin</option>
                                            </select>
                                        </div>
                                        <div class="flex items-center justify-between">
                                            <button type="submit"
                                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Thêm</button>
                                            <button type="button" id="cancelAddUser"
                                                class="bg-gray-400 hover:bg-gray-500 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Hủy</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="editUserModal" class="modal fixed z-10 inset-0 overflow-y-auto hidden"
                        aria-labelledby="modal-title" role="dialog" aria-modal="true">
                        <div class="flex items-center justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                            <div class="fixed inset-0 transition-opacity" aria-hidden="true">
                                <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
                            </div>
                            <span class="hidden sm:inline-block sm:align-middle sm:h-screen"
                                aria-hidden="true">&#8203;</span>
                            <div
                                class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                    <h3 class="text-lg font-bold leading-6 text-gray-900" id="modal-title">Cập nhật Người Dùng</h3>
                                    <form id="editUserForm" action="edit_user_process.php" method="POST">
                                        <input type="hidden" id="editUserId" name="id">
                                        <div class="mb-4">
                                            <label for="editName" class="block text-gray-700 text-sm font-bold mb-2">Tên:</label>
                                            <input type="text" id="editName" name="name"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                        </div>
                                        <div class="mb-4">
                                            <label for="editEmail" class="block text-gray-700 text-sm font-bold mb-2">Email:</label>
                                            <input type="email" id="editEmail" name="email"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                        </div>
                                        <div class="mb-4">
                                            <label for="editPassword" class="block text-gray-700 text-sm font-bold mb-2">Mật khẩu mới (để trống nếu không đổi):</label>
                                            <input type="password" id="editPassword" name="password"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                        </div>
                                        <div class="mb-4">
                                            <label for="editRole" class="block text-gray-700 text-sm font-bold mb-2">Vai trò:</label>
                                            <select id="editRole" name="role"
                                                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                required>
                                                <option value="user">User</option>
                                                <option value="admin">Admin</option>
                                            </select>
                                        </div>
                                        <div class="flex items-center justify-between">
                                            <button type="submit"
                                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Lưu</button>
                                            <button type="button" id="cancelEditUser"
                                                class="bg-gray-400 hover:bg-gray-500 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Hủy</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </section>
        </div>
    </main>


    <script>
        /*Toggle dropdown list*/
        function toggleDD(myDropMenu) {
            document.getElementById(myDropMenu).classList.toggle("invisible");
        }
        /*Filter dropdown options*/
        function filterDD(myDropMenu, myDropMenuSearch) {
            var input, filter, ul, li, a, i;
            input = document.getElementById(myDropMenuSearch);
            filter = input.value.toUpperCase();
            div = document.getElementById(myDropMenu);
            a = div.getElementsByTagName("a");
            for (i = 0; i < a.length; i++) {
                if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
                    a[i].style.display = "";
                } else {
                    a[i].style.display = "none";
                }
            }
        }
        // Close the dropdown menu if the user clicks outside of it
        window.onclick = function (event) {
            if (!event.target.matches('.drop-button') && !event.target.matches('.drop-search')) {
                var dropdowns = document.getElementsByClassName("dropdownlist");
                for (var i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (!openDropdown.classList.contains('invisible')) {
                        openDropdown.classList.add('invisible');
                    }
                }
            }
        }

        // JavaScript for User Management

        const addUserModal = document.getElementById('addUserModal');
        const addUserBtn = document.getElementById('addUserBtn');
        const cancelAddUserBtn = document.getElementById('cancelAddUser');

        const editUserModal = document.getElementById('editUserModal');
        const cancelEditUserBtn = document.getElementById('cancelEditUser');

        addUserBtn.addEventListener('click', () => {
            addUserModal.classList.remove('hidden');
        });

        cancelAddUserBtn.addEventListener('click', () => {
            addUserModal.classList.add('hidden');
        });

        cancelEditUserBtn.addEventListener('click', () => {
            editUserModal.classList.add('hidden');
        });

        // Close modal when clicking outside
        window.addEventListener('click', (event) => {
            if (event.target == addUserModal) {
                addUserModal.classList.add('hidden');
            }
            if (event.target == editUserModal) {
                editUserModal.classList.add('hidden');
            }
        });

        // Function to fetch and display users
        function fetchAndDisplayUsers() {
            fetch('get_users.php')
                .then(response => response.json())
                .then(users => {
                    const tableBody = document.getElementById('userTableBody');
                    tableBody.innerHTML = ''; // Clear existing rows

                    users.forEach(user => {
                        let row = document.createElement('tr');
                        row.innerHTML = `
                            <td class="border px-4 py-2">${user.id}</td>
                            <td class="border px-4 py-2">${user.name}</td>
                            <td class="border px-4 py-2">${user.email}</td>
                            <td class="border px-4 py-2">${user.role}</td>
<!-- Continuing from where the HTML was cut off -->
                            <td class="border px-4 py-2">
                                <button onclick="editUser(${user.id}, '${user.name}', '${user.email}', '${user.role}')" 
                                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded mr-2">
                                    <i class="fas fa-edit"></i> Sửa
                                </button>
                                <button onclick="deleteUser(${user.id})" 
                                    class="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-2 rounded">
                                    <i class="fas fa-trash-alt"></i> Xóa
                                </button>
                            </td>
                        `;
                        tableBody.appendChild(row);
                    });
                })
                .catch(error => {
                    console.error('Error fetching users:', error);
                    alert('Lỗi khi tải danh sách người dùng');
                });
        }

        // Function to handle user edit
        function editUser(id, name, email, role) {
            document.getElementById('editUserId').value = id;
            document.getElementById('editName').value = name;
            document.getElementById('editEmail').value = email;
            document.getElementById('editRole').value = role;
            editUserModal.classList.remove('hidden');
        }

        // Function to handle user deletion
        function deleteUser(userId) {
            if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
                const formData = new FormData();
                formData.append('user_id', userId);

                fetch('delete_user_process.php', {
                    method: 'POST',
                    body: formData
                })
                .then(response => response.text())
                .then(result => {
                    alert(result);
                    fetchAndDisplayUsers(); // Refresh the table
                })
                .catch(error => {
                    console.error('Error deleting user:', error);
                    alert('Lỗi khi xóa người dùng');
                });
            }
        }

        // Handle form submission for adding users
        document.getElementById('addUserForm').addEventListener('submit', function(event) {
            event.preventDefault();
            
            const formData = new FormData(this);

            fetch('add_user_process.php', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(result => {
                alert(result);
                addUserModal.classList.add('hidden');
                this.reset();
                fetchAndDisplayUsers(); // Refresh the table
            })
            .catch(error => {
                console.error('Error adding user:', error);
                alert('Lỗi khi thêm người dùng');
            });
        });

        // Handle form submission for editing users
        document.getElementById('editUserForm').addEventListener('submit', function(event) {
            event.preventDefault();
            
            const formData = new FormData(this);

            fetch('edit_user_process.php', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(result => {
                alert(result);
                editUserModal.classList.add('hidden');
                fetchAndDisplayUsers(); // Refresh the table
            })
            .catch(error => {
                console.error('Error editing user:', error);
                alert('Lỗi khi sửa thông tin người dùng');
            });
        });

        // Load users when page loads
        document.addEventListener('DOMContentLoaded', fetchAndDisplayUsers);
    </script>
</body>

</html>