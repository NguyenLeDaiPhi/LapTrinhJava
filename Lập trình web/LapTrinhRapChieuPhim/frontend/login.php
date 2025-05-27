<?php
session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Quản Lí Rạp Phim</title>
	<link href="css/bootstrap.min.css" rel="stylesheet" >
	<link href="css/font-awesome.min.css" rel="stylesheet" >
	<link href="css/global.css" rel="stylesheet">
	<link href="css/blog.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Platypi:ital,wght@0,300..800;1,300..800&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="js/bootstrap.bundle.min.js"></script>

</head>
<body>

<div class="main_o main">
 <div class="main_o1 bg_back">
   <section id="header">
<nav class="navbar navbar-expand-md navbar-light px_4" id="navbar_sticky">
  <div class="container-fluid">
    <a class="navbar-brand  p-0 fw-bold text-white" href="index.html"><i class="fa fa-modx col_oran"></i> Quản Lí Rạp Phim </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	   <ul class="navbar-nav mb-0 ms-auto">
	    
        <li class="nav-item">
          <a class="nav-link" aria-current="page" href="index.html">Trang chủ </a>
        </li>
		 
		<li class="nav-item">
          <a class="nav-link" href="about.html">Thông tin rạp </a>
        </li>
		
		<li class="nav-item">
			<a class="nav-link" href="movies.html">Phim </a>
		  </li>
  
		<!-- <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Phim 
          </a>
          <ul class="dropdown-menu drop_1" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="movies.html"><i class="fa fa-chevron-right font_12 me-1"></i> Phim </a></li>
            <li><a class="dropdown-item border-0" href="detail.html"><i class="fa fa-chevron-right font_12 me-1"></i> Chi tiết phim </a></li>
          </ul>
        </li> -->
		
		<li class="nav-item dropdown">
			<li class="nav-item">
				<a class="nav-link" href="blog.html">Blogs </a>
			  </li>
        </li>
		
		<li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Pages
          </a>
          <ul class="dropdown-menu drop_1" aria-labelledby="navbarDropdown">
		    <li><a class="dropdown-item" href="faq.html"><i class="fa fa-chevron-right font_12 me-1"></i> Câu hỏi thường gặp </a></li>
            <li><a class="dropdown-item" href="login.html"><i class="fa fa-chevron-right font_12 me-1"></i> Đăng nhập </a></li>
            <li><a class="dropdown-item" href="register.php"><i class="fa fa-chevron-right font_12 me-1"></i> Đăng kí </a></li>
			<li><a class="dropdown-item border-0" href="ticket.html"><i class="fa fa-chevron-right font_12 me-1"></i> Vé </a></li>
          </ul>
        </li>
			
		<li class="nav-item">
          <a class="nav-link" href="contact.html">Thông tin liên lạc </a>
        </li>
      </ul>
      <ul class="navbar-nav mb-0 ms-auto">
	       <li class="nav-item">
          <a class="nav-link fs-5 drop_icon" data-bs-target="#exampleModal2" data-bs-toggle="modal" href="#"><i class="fa fa-search"></i></a>
        </li>
		    <li class="nav-item">
          <a class="nav-link fs-5 drop_icon" href="login.html"><i class="fa fa-user"></i></a>
        </li>
      </ul>
    </div>
  </div>
</nav>
</section>
   <section id="center" class="centre_o  pt-5 pb-5">
 <div class="container-xl">
  <div class="row centre_o1 text-center">
    <div class="col-md-12">
      <h1 class="text-white font_60">Đăng nhập </h1>
	  <h5 class="mb-0 mt-3 fw-normal col_oran"><a class="text-light" href="index.html">Trang chủ</a> <span class="mx-2 text-muted">/</span> Đăng nhập </h5>
   </div>
  </div>
 </div>
</section>
 </div>
</div>

<div class="border_dashed">

</div>

<section id="login" class="p_3">
	<div class="container-xl">
	  <div class="row login_1">
		<div class="col-md-12">
		  <div class="login_1m p-5 px-4 bg-light w-50 mx-auto">
   
			<?php
			if (isset($_SESSION["login_error"])) {
			  echo '<p class="alert alert-danger">' . $_SESSION["login_error"] . '</p>';
			  unset($_SESSION["login_error"]);
			}
			?>
   
			<form action="../backend/login_process.php" method="POST">
			  <h6 class="mb-3 fw-bold">Email</h6>
			  <input class="form-control" name="email" placeholder="Email" type="email" required>
   
			  <h6 class="mb-3 fw-bold mt-4">Mật khẩu</h6>
			  <input class="form-control" name="password" placeholder="Mật khẩu" type="password" required>
   
			  <div class="login_1mi row mt-3">
				<div class="col-md-6 col-6">
				  <div class="login_1mil">
					<div class="form-check">
					  <input type="checkbox" class="form-check-input" id="customCheck1">
					  <label class="form-check-label" for="customCheck1">Nhớ tôi</label>
					</div>
				  </div>
				</div>
				<div class="col-md-6 col-6 text-end">
				  <h6 class="mb-0"><a href="forgot-password.php">Quên mật khẩu?</a></h6>
				</div>
			  </div>
   
			  <button type="submit" class="btn btn-primary mt-3 w-100">Đăng nhập</button>
   
			</form>
   
			<p class="mt-3 mb-0 text-center">
			  Không có tài khoản? <a class="fw-bold a_tag" href="register.php">Tạo tài khoản mới</a>
			</p>
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


<script src="js/common.js"></script>

</body>


</html>