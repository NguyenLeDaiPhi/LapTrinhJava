<?php
session_start();
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Quản lí rạp phim</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/font-awesome.min.css" rel="stylesheet" />
    <link href="css/global.css" rel="stylesheet" />
    <link href="css/index.css" rel="stylesheet" />
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
    <div class="main clearfix position-relative">
      <div
        class="modal fade"
        id="exampleModal2"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        style="display: none; top: 0"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content bg-transparent border-0">
            <div class="modal-header border-0">
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              >
                <i class="fa fa-close"></i>
              </button>
            </div>
            <div class="modal-body p-0">
              <div class="search_1">
                <div class="input-group">
                  <input
                    type="text"
                    class="form-control bg-white border-0"
                    placeholder="Search..."
                  />
                  <span class="input-group-btn">
                    <button
                      class="btn btn-primary bg_oran border_1 rounded-0 p-3 px-4"
                      type="button"
                    >
                      <i class="fa fa-search"></i>
                    </button>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="main_1 clearfix position-absolute top-0 w-100">
        <section id="header">
          <nav
            class="navbar navbar-expand-md navbar-light px_4"
            id="navbar_sticky"
          >
            <div class="container-fluid">
              <a class="navbar-brand p-0 fw-bold text-white" href="index.html"
                ><i class="fa fa-modx col_oran"></i> Quản lí rạp phim
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
                    <a
                      class="nav-link active"
                      aria-current="page"
                      href="index.html"
                      >Trang chủ</a
                    >
                  </li>

                  <li class="nav-item">
                    <a class="nav-link" href="about.html">Thông tin rạp</a>
                  </li>

                  <!-- <li class="nav-item dropdown">
                    <a
                      class="nav-link dropdown-toggle"
                      href="#"
                      id="navbarDropdown"
                      role="button"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      Các loại phim
                    </a>
                    <ul
                      class="dropdown-menu drop_1"
                      aria-labelledby="navbarDropdown"
                    >
                      <li>
                        <a class="dropdown-item" href="movies.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i>
                          Phim</a
                        >
                      </li>
                      <li>
                        <a class="dropdown-item border-0" href="detail.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i> Chi
                          tiết phim</a
                        >
                      </li>
                    </ul>
                  </li> -->

                  <li class="nav-item">
                    <a class="nav-link" href="blog.html">Blogs </a>
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
                      Pages
                    </a>
                    <ul
                      class="dropdown-menu drop_1"
                      aria-labelledby="navbarDropdown"
                    >
                      <li>
                        <a class="dropdown-item" href="faq.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i> Các
                          câu hỏi thường gặp
                        </a>
                      </li>
                      <li>
                        <a class="dropdown-item" href="login.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i> Đăng nhập
                        </a>
                      </li>
                      <li>
                        <a class="dropdown-item" href="register.html"
                          ><i class="fa fa-chevron-right font_12 me-1"></i> Đăng ký
                        </a>
                      </li>
                      <li>
                        <a class="dropdown-item border-0" href="show_ticket.php"
                          ><i class="fa fa-chevron-right font_12 me-1"></i> Vé
                        </a>
                      </li>
                    </ul>
                  </li>

                  <li class="nav-item">
                    <a class="nav-link" href="contact.html"
                      >Thông tin liên lạc
                    </a>
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
                    <a class="nav-link fs-5 drop_icon" href="login.html"
                      ><i class="fa fa-user"></i
                    ></a>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </section>
      </div>
      <div class="main_2 clearfix">
        <section id="center" class="center_home">
          <div
            id="carouselExampleCaptions"
            class="carousel slide"
            data-bs-ride="carousel"
          >
            <div class="carousel-indicators">
              <button
                type="button"
                data-bs-target="#carouselExampleCaptions"
                data-bs-slide-to="0"
                class="active"
                aria-label="Slide 1"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleCaptions"
                data-bs-slide-to="1"
                aria-label="Slide 2"
                class=""
                aria-current="true"
              ></button>
              <button
                type="button"
                data-bs-target="#carouselExampleCaptions"
                data-bs-slide-to="2"
                aria-label="Slide 3"
              ></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="img/11.jpg" class="d-block w-100" alt="..." />
                <div class="carousel-caption d-md-block">
                  <h3 class="col_oran">Phim viễn tưởng</h3>
                  <h1 class="text-white mt-3">
                    Venom <br />
                    Season 3
                  </h1>
                  <p class="mt-3 text-light w-75">
                    Sau những sự kiện đầy kịch tính của phần trước, Eddie Brock
                    (Tom Hardy) và sinh vật cộng sinh Venom tiếp tục hành trình
                    chạy trốn khỏi sự truy đuổi của chính phủ và những thế lực
                    nguy hiểm khác. Tuy nhiên, lần này, họ không chỉ đối đầu với
                    con người mà còn phải đương đầu với một mối đe dọa lớn hơn
                    từ vũ trụ – kẻ thù có thể thay đổi vận mệnh của cả Trái Đất.
                  </p>
                  <ul class="mb-0 mt-4">
                    <li class="d-inline-block">
                      <a class="button" href="#"
                        ><i class="fa fa-check-circle me-1 font_14"></i> Thông
                        tin chi tiết
                      </a>
                    </li>
                    <li class="d-inline-block ms-2">
                      <a class="button_1" href="ticket.html"
                        ><i class="fa fa-check-circle me-1 font_14"></i> Mua vé
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="carousel-item">
                <img src="img/2.jpg" class="d-block w-100" alt="..." />
                <div class="carousel-caption d-md-block">
                  <h3 class="col_oran">Phim hành động</h3>
                  <h1 class="text-white mt-3">Spider-man no way home <br /></h1>
                  <p class="mt-3 text-light w-75">
                    Sau sự kiện ở Spider-Man: Far From Home, danh tính của Peter
                    Parker (Tom Holland) bị tiết lộ cho cả thế giới bởi
                    Mysterio. Muốn lấy lại cuộc sống bình thường, Peter tìm đến
                    Doctor Strange (Benedict Cumberbatch) để nhờ thực hiện một
                    câu thần chú nhằm xóa trí nhớ mọi người về danh tính của
                    mình. Tuy nhiên, do Peter liên tục can thiệp vào bùa chú, nó
                    bị mất kiểm soát, làm rách vết nứt của đa vũ trụ
                    (Multiverse).
                  </p>
                  <ul class="mb-0 mt-4">
                    <li class="d-inline-block">
                      <a class="button" href="#"
                        ><i class="fa fa-check-circle me-1 font_14"></i> Thông
                        tin chi tiết
                      </a>
                    </li>
                    <li class="d-inline-block ms-2">
                      <a class="button_1" href="ticket.html"
                        ><i class="fa fa-check-circle me-1 font_14"></i> Mua vé
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="carousel-item">
                <img src="img/3.jpg" class="d-block w-100" alt="..." />
                <div class="carousel-caption d-md-block">
                  <h3 class="col_oran">Phim tâm lý</h3>
                  <h1 class="text-white mt-3">Inside out 2 <br /></h1>
                  <p class="mt-3 text-light w-75">
                    Hai năm sau khi chuyển đến San Francisco, cô bé Riley
                    Andersen, nay 13 tuổi, bước vào trung học. Các cảm xúc nhân
                    cách hóa của cô – Vui vẻ, Buồn bã, Sợ hãi, Ghê tởm và Giận
                    dữ – giờ đây quản lý một phần mới trong tâm trí Riley gọi là
                    "Cảm giác về Bản thân", nơi lưu giữ những ký ức và cảm xúc
                    hình thành niềm tin của cô. Vui vẻ, với mong muốn lấp đầy
                    "Cảm giác về Bản thân" bằng những ký ức tốt đẹp, đã tạo ra
                    một cơ chế đẩy những ký ức xấu ra sau tâm trí của Riley.
                  </p>
                  <ul class="mb-0 mt-4">
                    <li class="d-inline-block">
                      <a class="button" href="#"
                        ><i class="fa fa-check-circle me-1 font_14"></i> Thông
                        tin chi tiết
                      </a>
                    </li>
                    <li class="d-inline-block ms-2">
                      <a class="button_1" href="ticket.html"
                        ><i class="fa fa-check-circle me-1 font_14"></i> Mua vé
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide="prev"
            >
              <span
                class="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide="next"
            >
              <span
                class="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </section>
      </div>
    </div>

    <div class="border_dashed"></div>

    <section id="trend" class="p_3 pt-0">
      <div class="container-xl">
        <div class="row trend_1 text-center mb-4">
          <div class="col-md-12">
            <span class="fa fa-film col_oran"></span>
            <h6 class="text-muted mt-3">Xem Phim Mới</h6>
            <h1 class="mb-0 font_50">Phim đang chiếu</h1>
          </div>
        </div>
        <div class="row trend_2">
        <div class="movie-container">
<?php
    include "../backend/movies.php"; // Include the movies class
    $movies = new Movies();
    $movies->showMovies();
    ?>
</div>
        </div>
      </div>
    </section>

    <section id="feat" class="p_3 bg-light">
      <div class="container-xl">
        <div class="row feat_1 mb-4">
          <div class="col-md-6">
            <div class="feat_1l">
              <span class="fa fa-film col_oran"></span>
              <h6 class="text-muted mt-3">Tìm kiếm phim</h6>
              <h1 class="mb-0 font_50">Phim nổi bật nhất</h1>
            </div>
          </div>
          <div class="col-md-6">
            <div class="feat_1r mt-5">
              <p class="mb-0"></p>
            </div>
          </div>
        </div>
        <div class="row feat_2">
          <div class="col-md-4">
            <div class="feat_2i position-relative">
              <div class="feat_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/9.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div class="feat_2i2 position-absolute bg-white shadow_box p-4">
                <h5><a href="detail.html">Spider-Man: No Way Home </a></h5>
                <h6 class="font_14 mt-3">
                  <i class="fa fa-tag col_oran me-1"></i> Hành Động
                  <i class="fa fa-clock-o col_oran me-1 ms-3"></i> Thời lượng
                  148 phút
                </h6>
                <ul class="mb-0 mt-3 font_14">
                  <li class="d-inline-block"><a href="#" class="open-video-modal" data-bs-toggle="modal" 
                    data-bs-target="#videoModal" 
                    data-video-url="https://www.youtube.com/watch?v=JfVOs4VSpmA">
                   Xem Trailer
                 </a>
               </li>
                  <li class="d-inline-block ms-2">
                    <a href="ticket.html">Mua Vé </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="feat_2i position-relative">
              <div class="feat_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/10.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div class="feat_2i2 position-absolute bg-white shadow_box p-4">
                <h5><a href="detail.html">Chú Chuột Đầu Bếp </a></h5>
                <h6 class="font_14 mt-3">
                  <i class="fa fa-tag col_oran me-1"></i> Hoạt Hình
                  <i class="fa fa-clock-o col_oran me-1 ms-3"></i> Thời lượng
                  110 phút
                </h6>
                <ul class="mb-0 mt-3 font_14">
                  <li class="d-inline-block"><a href="#" class="open-video-modal" data-bs-toggle="modal" 
                    data-bs-target="#videoModal" 
                    data-video-url="https://www.youtube.com/embed/NgsQ8mVkN8w">
                   Xem Trailer
                 </a>
               </li>                  
               <li class="d-inline-block ms-2">
                    <a href="ticket.html">Mua Vé </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="feat_2i position-relative">
              <div class="feat_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/11.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div class="feat_2i2 position-absolute bg-white shadow_box p-4">
                <h5><a href="detail.html">Venom Season 3 </a></h5>
                <h6 class="font_14 mt-3">
                  <i class="fa fa-tag col_oran me-1"></i> Viễn Tưởng 
                  <i class="fa fa-clock-o col_oran me-1 ms-3"></i> Thời lượng
                  110 phút
                </h6>
                <ul class="mb-0 mt-3 font_14">
                  <li class="d-inline-block"><a href="#" class="open-video-modal" data-bs-toggle="modal" 
                    data-bs-target="#videoModal" 
                    data-video-url="https://www.youtube.com/embed/id1rfr_KZWg">
                   Xem Trailer
                 </a>
               </li>                  
                  <li class="d-inline-block ms-2">
                    <a href="ticket.html">Mua Vé </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="row feat_3 border_1 p-3 mx-auto">
          <div class="col-md-9">
            <div class="feat_3l mt-1">
              <p class="mb-0">
                <span class="fs-5 col_oran fw-bold align-middle">23,00+</span>
                Phim bạn có thể xem
              </p>
            </div>
          </div>
          <div class="col-md-3">
            <div class="feat_3r text-end">
              <h6 class="mb-0">
                <a class="button_2 p-2 px-3 font_14" href="#">Khám phá ngay </a>
              </h6>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- <section id="choose">
      <div class="choose_m bg_backo pt-5 pb-5">
        <div class="container-xl">
          <div class="choose_1 row">
            <div class="col-md-6 col-sm-6">
              <div class="choose_1l">
                <span class="fa fa-film col_oran"></span>
                <h6 class="text-white-50 mt-3">Phim tài liệu</h6>
                <h1 class="font_50 text-white">Openheimer</h1>
                <p class="mt-3 text-white-50">
                  Phim theo chân Oppenheimer từ thời trẻ, khi ông trở thành nhà
                  khoa học lỗi lạc, đến khi lãnh đạo Dự án Manhattan, tạo ra quả
                  bom nguyên tử đầu tiên. Bộ phim không chỉ nói về khoa học mà
                  còn khai thác sâu về tâm lý của Oppenheimer, khi ông đối diện
                  với hậu quả của phát minh mang tính hủy diệt này.
                </p>
                <h6 class="mb-0 mt-4">
                  <a class="button_1" href="#">Thêm thông tin </a>
                </h6>
              </div>
            </div>
            <div class="col-md-6 col-sm-6">
              <div class="choose_1r text-center mt-5">
                <h4 class="text-white mb-0">
                  Xem Trailer
                  <span class="ms-2"
                    ><a
                      class="col_oran"
                      href="https://youtu.be/uYPbbksJxIg?si=kQHyvx1qzJzpzUJH"
                      target="_blank"
                    >
                      <i class="fa fa-play-circle align-middle"></i> </a
                  ></span>
                </h4>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
 -->
    <section id="gallery" class="px_4 p_3">
      <div class="container-fluid">
        <div class="row gallery_1 text-center mb-4">
          <div class="col-md-12">
            <h1 class="mb-0">Thư viện ảnh</h1>
          </div>
        </div>
        <div class="row gallery_2">
          <div class="col">
            <div class="gallery_2i position-relative">
              <div class="gallery_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/12.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div
                class="gallery_2i2 bg_backn text-center px-4 position-absolute w-100 h-100 top-0"
              >
                <span class="d-inline-block"
                  ><a
                    class="fs-1 text-white"
                    data-bs-target="#exampleModal3"
                    data-bs-toggle="modal"
                    href="#"
                    ><i class="fa fa-instagram"></i></a
                ></span>
              </div>
              <div
                class="modal fade"
                id="exampleModal3"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                style="display: none; top: 0"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content bg-transparent border-0">
                    <div class="modal-header border-0">
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      >
                        <i class="fa fa-close"></i>
                      </button>
                    </div>
                    <div class="modal-body">
                      <img src="img/12.jpg" class="w-100" alt="abc" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="gallery_2i position-relative">
              <div class="gallery_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/13.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div
                class="gallery_2i2 bg_backn text-center px-4 position-absolute w-100 h-100 top-0"
              >
                <span class="d-inline-block"
                  ><a
                    class="fs-1 text-white"
                    data-bs-target="#exampleModal4"
                    data-bs-toggle="modal"
                    href="#"
                    ><i class="fa fa-instagram"></i></a
                ></span>
              </div>
              <div
                class="modal fade"
                id="exampleModal4"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                style="display: none; top: 0"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content bg-transparent border-0">
                    <div class="modal-header border-0">
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      >
                        <i class="fa fa-close"></i>
                      </button>
                    </div>
                    <div class="modal-body">
                      <img src="img/13.jpg" class="w-100" alt="abc" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="gallery_2i position-relative">
              <div class="gallery_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/14.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div
                class="gallery_2i2 bg_backn text-center px-4 position-absolute w-100 h-100 top-0"
              >
                <span class="d-inline-block"
                  ><a
                    class="fs-1 text-white"
                    data-bs-target="#exampleModal5"
                    data-bs-toggle="modal"
                    href="#"
                    ><i class="fa fa-instagram"></i></a
                ></span>
              </div>
              <div
                class="modal fade"
                id="exampleModal5"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                style="display: none; top: 0"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content bg-transparent border-0">
                    <div class="modal-header border-0">
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      >
                        <i class="fa fa-close"></i>
                      </button>
                    </div>
                    <div class="modal-body">
                      <img src="img/14.jpg" class="w-100" alt="abc" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="gallery_2i position-relative">
              <div class="gallery_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/15.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div
                class="gallery_2i2 bg_backn text-center px-4 position-absolute w-100 h-100 top-0"
              >
                <span class="d-inline-block"
                  ><a
                    class="fs-1 text-white"
                    data-bs-target="#exampleModal6"
                    data-bs-toggle="modal"
                    href="#"
                    ><i class="fa fa-instagram"></i></a
                ></span>
              </div>
              <div
                class="modal fade"
                id="exampleModal6"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                style="display: none; top: 0"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content bg-transparent border-0">
                    <div class="modal-header border-0">
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      >
                        <i class="fa fa-close"></i>
                      </button>
                    </div>
                    <div class="modal-body">
                      <img src="img/15.jpg" class="w-100" alt="abc" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="gallery_2i position-relative">
              <div class="gallery_2i1">
                <div class="grid clearfix">
                  <figure class="effect-jazz mb-0">
                    <a href="#"
                      ><img src="img/16.jpg" class="w-100" alt="abc"
                    /></a>
                  </figure>
                </div>
              </div>
              <div
                class="gallery_2i2 bg_backn text-center px-4 position-absolute w-100 h-100 top-0"
              >
                <span class="d-inline-block"
                  ><a
                    class="fs-1 text-white"
                    data-bs-target="#exampleModal7"
                    data-bs-toggle="modal"
                    href="#"
                    ><i class="fa fa-instagram"></i></a
                ></span>
              </div>
              <div
                class="modal fade"
                id="exampleModal7"
                tabindex="-1"
                aria-labelledby="exampleModalLabel"
                style="display: none; top: 0"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content bg-transparent border-0">
                    <div class="modal-header border-0">
                      <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                      >
                        <i class="fa fa-close"></i>
                      </button>
                    </div>
                    <div class="modal-body">
                      <img src="img/16.jpg" class="w-100" alt="abc" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="testim" class="p_3 bg-light carousel_p">
      <div class="container-xl">
        <div class="row testim_1">
          <div class="col-md-6">
            <div class="testim_1l">
              <span class="fa fa-film col_oran"></span>
              <h6 class="text-muted mt-3">Phản hồi</h6>
              <h1 class="mb-0 font_50">Họ đánh giá gì về chúng tôi ?</h1>
              <p class="mt-3">Tuyệt đối điện ảnh</p>
              <h6 class="mb-0 mt-4">
                <a class="button_2" href="#">Xem mọi phản hồi </a>
              </h6>
            </div>
          </div>
          <div class="col-md-6">
            <div class="testim_1r mt-5">
              <div
                id="carouselExampleCaptions2"
                class="carousel slide"
                data-bs-ride="carousel"
              >
                <div class="carousel-indicators">
                  <button
                    type="button"
                    data-bs-target="#carouselExampleCaptions2"
                    data-bs-slide-to="0"
                    class="active"
                    aria-label="Slide 1"
                  ></button>
                  <button
                    type="button"
                    data-bs-target="#carouselExampleCaptions2"
                    data-bs-slide-to="1"
                    aria-label="Slide 2"
                    class=""
                    aria-current="true"
                  ></button>
                </div>
                <div class="carousel-inner">
                  <div class="carousel-item active">
                    <div class="testim_1i row">
                      <div class="col-md-12">
                        <div class="testim_1i1 bg-white p-4 clearfix">
                          <span
                            class="d-inline-block bg_oran text-white text-center span_1 rounded-circle"
                            ><i class="fa fa-quote-left"></i
                          ></span>
                          <div class="testim_1i1i clearfix mt-3">
                            <span
                              ><img
                                src="img/19.jpg"
                                alt="abc"
                                class="rounded-circle me-3 float-start"
                            /></span>
                            <h6 class="mb-0 fw-bold text-uppercase">
                              Huy đẹp zai <br />
                              <span class="col_oran font_14 fw-normal"
                                >Khách hàng
                              </span>
                            </h6>
                          </div>
                          <p class="mb-0 mt-3">
                            Rạp phim rất tinh tế và chuyên nghiệp, phim rõ nét
                            và âm thanh sống động, nhân viên phục vụ nhiệt tình
                            và web mua vé siuuuuuuuu mượt mà ^^
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="carousel-item">
                    <div class="testim_1i row">
                      <div class="col-md-12">
                        <div class="testim_1i1 bg-white p-4 clearfix">
                          <span
                            class="d-inline-block bg_oran text-white text-center span_1 rounded-circle"
                            ><i class="fa fa-quote-left"></i
                          ></span>
                          <div class="testim_1i1i clearfix mt-3">
                            <span
                              ><img
                                src="img/20.jpg"
                                alt="abc"
                                class="rounded-circle me-3 float-start"
                            /></span>
                            <h6 class="mb-0 fw-bold text-uppercase">
                              Huyền xinh iu <br />
                              <span class="col_oran font_14 fw-normal"
                                >Khách hàng
                              </span>
                            </h6>
                          </div>
                          <p class="mb-0 mt-3">
                            Lần đầu tuii được trải nghiệm 1 web siêu thân thiện
                            với người dùng này, mong web tiếp tục phát huy nhé
                            =]]]]
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="trend_o" class="p_3 px_4">
        <div class="row trend_o1 mt-4 w-75 mx-auto">
          <div class="col-md-12">
            <div class="trend_o1i1 bg_backn pt-5 pb-5 px-4">
              <div class="trend_o1i1i row">
                <div class="col-md-8">
                  <div class="trend_o1i1il pt-4">
                    <h2 class="mb-0 text-white">
                      Giảm 40% vé cho hs,sv khi đặt sớm
                    </h2>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="trend_o1i1ir text-end">
                    <h6 class="mb-0 mt-4">
                      <a class="button_3" href="#">Đặt vé ngay ! </a>
                    </h6>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="footer" class="p_3 bg-black">
      <div class="container-xl">
        <div class="footer_1 row pb-4">
          <div class="col-md-3">
            <div class="footer_1l">
              <h4 class="mb-0">
                <a class="fw-bold text-white" href="index.html"
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
              <a class="col_oran fw-bold" href="index.html"
                >Quản Lí Rạp Phim
              </a>
            </p>
          </div>
        </div>
      </div>
    </section>

    <!-- <div
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
    </div> -->
    <div class="modal fade" id="videoModal" tabindex="-1" aria-labelledby="videoModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg modal-dialog-centered">
          <div class="modal-content">
              <div class="modal-header">
                  <h2 class="modal-title" id="videoModalLabel">Video</h2>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                  <div class="ratio ratio-16x9">
                      <iframe id="youtubeVideo" src="" title="YouTube video player" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope" loading="lazy" allowfullscreen></iframe>
                  </div>
              </div>
          </div>
      </div>
  </div>
    <!-- <script>
      var phim = document.getElementById("phimmoi");
      templateVideoModal.addEventListener("hide.bs.modal", function () {
        var iframe = document.getElementById("youtubeVideo");
        iframe.src = "";
      });

      templateVideoModal.addEventListener("show.bs.modal", function () {
        var iframe = document.getElementById("youtubeVideo");
        iframe.src = "https://www.youtube.com/embed/XLuL_TXbK1g";
      });
    </script>
    
 -->
    <script src="js/common.js"></script>
  </body>
</html>
/>
