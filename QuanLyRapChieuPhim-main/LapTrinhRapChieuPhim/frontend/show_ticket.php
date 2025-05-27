<?php
session_start();
require_once '../database/database.php';

if (!isset($_SESSION['user_id'])) {
    die("Bạn cần đăng nhập để xem vé.");
}

$user_id = $_SESSION['user_id'];

$db = new Database();
$conn = $db->getConnection();

// Lấy vé mới nhất của user
$sql = "SELECT 
        b.id AS booking_id, 
        m.title, 
        m.price, 
        b.seats, 
        b.booking_time, 
        p.payment_method, 
        p.payment_status 
    FROM bookings b 
    JOIN movies m ON b.movie_id = m.id 
    LEFT JOIN payments p ON b.id = p.booking_id 
    WHERE b.user_id = ? 
    ORDER BY b.booking_time DESC 
    LIMIT 1";

$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    echo "Bạn chưa có vé nào.";
    exit;
}

$ticket = $result->fetch_assoc();
// Tính tổng tiền
$seatList = explode(',', $ticket['seats']);
$seatCount = count($seatList);
$pricePerSeat = $ticket['price'];
$comboPrice = 50000;

// Giả sử nếu người dùng chọn combo, họ ghi thêm ghế là "combo" vào chuỗi seats
$hasCombo = in_array('combo', array_map('trim', $seatList));

$total = $seatCount * $pricePerSeat;
if ($hasCombo) {
    $total += $comboPrice;
}

?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Movie Theme</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/font-awesome.min.css" rel="stylesheet" />
    <link href="css/global.css" rel="stylesheet" />
    <link href="css/list.css" rel="stylesheet" />
    <link
      href="https://fonts.googleapis.com/css2?family=Platypi:ital,wght@0,300..800;1,300..800&display=swap"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins&display=swap"
      rel="stylesheet"
    />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
  </head>
  <body>
  <body data-movie-id="<?= $movie['id'] ?>">
    <div class="main_o main">
      <div class="main_o1 bg_back">
        <section id="header">
          <nav
            class="navbar navbar-expand-md navbar-light px_4"
            id="navbar_sticky"
          >
            <div class="container-fluid">
              <a class="navbar-brand p-0 fw-bold text-white" href="index.php"
                ><i class="fa fa-modx col_oran"></i> Quản Lí Rạp Phim
              </a>
              <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent"
                aria-expanded="false"
                aria-label="Toggle navigation"
              >
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mb-0 ms-auto">
                  <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="index.php"
                      >Trang chủ
                    </a>
                  </li>

                  <li class="nav-item">
                    <a class="nav-link" href="about.html">Thông tin rạp </a>
                  </li>

                  <li class="nav-item dropdown">
                    <a
                      class="nav-link dropdown-toggle"
                      href="#"
                      id="navbarDropdown"
                      role="button"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      Blogs
                    </a>
                    <ul
                      class="dropdown-menu drop_1"
                      aria-labelledby="navbarDropdown"
                    >
                      <li>
                        <a class="dropdown-item" href="blog.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Blogs</a
                        >
                      </li>
                      <li>
                        <a
                          class="dropdown-item border-0"
                          href="blog_detail.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i> Blog
                          Details</a
                        >
                      </li>
                    </ul>
                  </li>

                  <li class="nav-item dropdown">
                    <a
                      class="nav-link dropdown-toggle"
                      href="#"
                      id="navbarDropdown"
                      role="button"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      Quản lý tài khoản
                    </a>
                    <ul
                      class="dropdown-menu drop_1"
                      aria-labelledby="navbarDropdown"
                    >
                      <li>
                        <a class="dropdown-item" href="faq.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Faqs</a
                        >
                      </li>
                      <li>
                        <a class="dropdown-item" href="login.php"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Đăng nhập</a
                        >
                      </li>
                      <li>
                        <a class="dropdown-item" href="register.php"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Đăng ký</a
                        >
                      </li>
                      <li>
                        <a class="dropdown-item border-0" href="show_ticket.php"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Vé</a
                        >
                      </li>
                      <li>
                        <a class="dropdown-item border-0" href="../backend/logout_process.php"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Đăng xuất</a
                        >
                      </li>
                    </ul>
                  </li>

                  <li class="nav-item">
                    <a class="nav-link" href="contact.html">Contact Us</a>
                  </li>
                </ul>
                <ul class="navbar-nav mb-0 ms-auto">
                  <li class="nav-item">
                    <a
                      class="nav-link fs-5 drop_icon"
                      data-bs-target="#exampleModal2"
                      data-bs-toggle="modal"
                      href="#"
                      ><i class="fa fa-search"></i
                    ></a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link fs-5 drop_icon" href="#"
                      ><i class="fa fa-user"></i
                    ></a>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </section>
        <section id="center" class="centre_o pt-5 pb-5">
          <div class="container-xl">
            <div class="row centre_o1 text-center">
              <div class="col-md-12">
                <h1 class="text-white font_60">Thông Tin Phim</h1>
                <h5 class="mb-0 mt-3 fw-normal col_oran">
                  <a class="text-light" href="index.php">Trang Chủ</a>
                  <span class="mx-2 text-muted">/</span> Movie Details
                </h5>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
    <div class="border_dashed"></div>
    <div style="display: flex; justify-content: center; align-items: center; min-height: 50vh; flex-direction: column;">
    <div class="ticket" style="text-align: center;">
        <h2>🎟️ Vé xem phim</h2>
        <p><strong>Phim:</strong> <?= htmlspecialchars($ticket['title']) ?></p>
        <p><strong>Ghế:</strong> <?= htmlspecialchars($ticket['seats']) ?></p>
        <p><strong>Thời gian đặt:</strong> <?= $ticket['booking_time'] ?></p>
        <p><strong>Giá vé 1 ghế:</strong> <?= $pricePerSeat ?> VNĐ</p>
        <p><strong>Số ghế:</strong> <?= htmlspecialchars($ticket['seats']) ?></p>

        <?php if ($hasCombo): ?>
            <p><strong>Combo bắp + nước:</strong> <?= $comboPrice ?> VNĐ</p>
        <?php endif; ?>

        <p><strong>Tổng cộng:</strong> <?= $total ?> VNĐ</p>
        <p><strong>Thanh toán:</strong> <?= $ticket['payment_status'] ?> (<?= $ticket['payment_method'] ?>)</p>
        <p><strong>Mã đặt vé:</strong> <?= $ticket['booking_id'] ?></p>
    </div>
    <div style="text-align: center; margin-top: 10px;">
        <a href="../frontend/index.php" style="
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        ">🏠 Quay lại trang chủ</a>
    </div>
</div>
<section id="footer" class="p_3 bg-black">
      <div class="container-xl">
        <div class="footer_1 row pb-4">
          <div class="col-md-3">
            <div class="footer_1l">
              <h4 class="mb-0">
                <a class="fw-bold text-white" href="index.php"
                  ><i class="fa fa-modx col_oran">
                    Chúc bạn có trải nghiệm tốt
                  </i>
                </a>
              </h4>
            </div>
          </div>
          <div class="col-md-9">
            <div class="footer_1r text-end">
              <ul class="mb-0">
                <li class="d-inline-block">
                  <a class="text-white-50 a_tag" href="#">Giúp đỡ</a>
                </li>
                <li class="d-inline-block text-white-50">/</li>
                <li class="d-inline-block me-2">
                  <a class="text-white-50 a_tag" href="#">
                    Chính sách và quyền riêng tư
                  </a>
                </li>

                <li class="d-inline-block">
                  <a
                    class="social_icon"
                    href="https://www.facebook.com/hoangductrungg"
                    target="_blank"
                  >
                    <i class="fa fa-facebook"></i>
                  </a>
                </li>

                <li class="d-inline-block">
                  <a
                    class="social_icon"
                    href="https://www.instagram.com/meopeongokngek/"
                    target="_blank"
                  >
                    <i class="fa fa-instagram"></i>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="footer_2 row mt-4">
          <div class="col-md-3">
            <div class="footer_2i">
              <p class="text-white fw-bold fs-5">
                Mua vé xem phim dễ dàng với Quản lí rạp chiếu phim
              </p>
              <h6 class="mb-0 mt-4">
                <a class="button" href="#">Hãy chọn vé </a>
              </h6>
            </div>
          </div>
          <div class="col-md-3">
            <div class="footer_2i">
              <h5 class="col_oran mb-4">Movies</h5>
              <div class="row footer_1ism">
                <h6 class="fw-normal col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Hành động </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Phiêu lưu </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Hoạt hình </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Anime </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Tài liệu </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6 mb-0">
                  <a class="text-white-50 a_tag" href="#"> Hài kịch</a>
                </h6>
              </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="footer_2i">
              <h5 class="col_oran mb-4">Các liên kết</h5>
              <div class="row footer_1ism">
                <h6 class="fw-normal col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Về chúng tôi </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#">
                    Tài khoản của tôi
                  </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Tin tức </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Sự kiện mới nhất </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6">
                  <a class="text-white-50 a_tag" href="#"> Chính sách </a>
                </h6>
                <h6 class="fw-normal mt-2 col-md-12 col-6 mb-0">
                  <a class="text-white-50 a_tag" href="#">
                    Thông tin liên lạc
                  </a>
                </h6>
              </div>
            </div>
          </div>
          <div class="col-md-3">
            <div class="footer_2i">
              <h5 class="col_oran mb-4">Thông báo mới</h5>
              <p class="text-white-50">
                Đăng ký gmail để nhận thông báo mới nhất từ chúng tôi
              </p>
              <div class="input-group bg-white">
                <input
                  type="text"
                  class="form-control bg-transparent rounded-0 border-0"
                  placeholder="Điền email của bạn"
                />
                <span class="input-group-btn">
                  <button
                    class="btn btn-primary bg-transparent rounded-0 border-0 col_oran p-3"
                    type="button"
                  >
                    <i class="fa fa-location-arrow"></i>
                  </button>
                </span>
              </div>
              <div class="form-check mt-3 text-white-50">
                <input class="form-check-input" type="checkbox" value="" />
                <label class="form-check-label" for="flexCheckDefault">
                  Tôi đồng ý với các điều khoản của công ty
                </label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="footer_b" class="pt-3 pb-3 bg-dark">
      <div class="container-xl">
        <div class="footer_b1 row text-center">
          <div class="col-md-12">
            <p class="mb-0 text-white-50">
              © 2025 Quản Lí Rạp Phim
              <a class="col_oran fw-bold" href="index.php"
                >Quản Lí Rạp Phim
              </a>
            </p>
          </div>
        </div>
      </div>
    </section>

    <!-- Video Modal -->
    <div
      class="modal fade"
      id="templateVideoModal"
      tabindex="-1"
      aria-labelledby="videoModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title" id="videoModalLabel">Video</h2>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <!-- YouTube Embed -->
            <div class="ratio ratio-16x9">
              <iframe
                id="youtubeVideo"
                src=""
                title="YouTube video player"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope"
                loading="lazy"
                allowfullscreen
              ></iframe>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- JavaScript to pause the video when modal is closed -->
    <script>
      let movie_id = $('body').data('movie-id');
      var templateVideoModal = document.getElementById("templateVideoModal");

      // Khi mở modal, chèn video vào iframe
      templateVideoModal.addEventListener("show.bs.modal", function () {
        var iframe = document.getElementById("youtubeVideo");
        iframe.src = "<?php echo str_replace("watch?v=", "embed/", $movie['trailer_link']); ?>";
    });
    if (window.history.replaceState) {
      const cleanUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
      window.history.replaceState({}, document.title, cleanUrl);
    }
      // Khi đóng modal, xóa src để dừng video
      templateVideoModal.addEventListener("hide.bs.modal", function () {
        var iframe = document.getElementById("youtubeVideo");
        iframe.src = "";
      });
    </script>

    <script src="js/common.js"></script>
  </body>
</html>