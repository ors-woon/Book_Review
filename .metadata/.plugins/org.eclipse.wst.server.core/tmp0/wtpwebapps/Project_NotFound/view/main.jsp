<%@ page language="java" contentType="text/html;" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link href="/NotFound/css/bootstrap.min.css" rel="stylesheet">
<script type="text/JavaScript"
	src="http://code.jquery.com/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/NotFound/script/board.js"></script>
<script type="text/javascript" src="/NotFound/script/popup.js"></script>
<link href="/NotFound/css/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html;">
<title>Insert title here</title>
</head>
<body>

	<header id="header">
	<div class="sid_bar">
		<div class="col-lg-6">
			<h2 style="">&nbsp;Editor</h2>
		</div>
		<div class="side_bar" style="color: black">
			<label for="exampleInputEmail1">Name : ${name}</label> <br> <label
				for="exampleInputEmail1">ID : ${id}</label>
			<button type="button" class="btn btn-default btn-sm"
				aria-label="Left Align"
				onclick="location.href='index.do?action=logout';">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			</button>

			<hr>
			<ul class="nav nav-pills nav-stacked">
				<li role="presentation" class="active"><a href="#">Home</a></li>
				<li role="presentation"><a href="#"
					onclick="layer_open('layer2');return false;">Write</a></li>
				<li role="presentation"><a href="#">MyPage</a></li>
			</ul>
			<hr>
		</div>
	</div>
	</header>
	<!--  여기부터 -->

	<div class="main_table">
		<!--center-->
		<div class="col-sm-8">

			<div class="row" style="margin-top: 5%; margin-left: 3%">
				<form action="/NotFound/main.do" method="post">
					<select name="search_value">
						<option value="writer">작성자</option>
						<option value="subject">글 제목</option>
						<option value="bookname">책 제목</option>
					</select> <input type="hidden" id="main_action" name=main_action
						value="search"> <input type="text" id="search_key"
						name="search_key"> <input type="submit"
						class="btn btn-primary" value="검색" />
				</form>

			</div>
			<div class="row" style="margin-top: 5%;">
				<div class="col-xs-14">
					<table class="table table-hover">
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제목</th>
							<th scope="col">책제목</th>
							<th scope="col">작성자</th>
						</tr>
						<c:choose>
							<c:when test="${fn:length(board) == 0}">
							
							</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${fn:length(board)-1}"
									step="1">
									<tr>
										<td width="10%" align="center">${board[i].num}</td>
										<td width="50%" align="center"><a
											href="/NotFound/main.do?main_action=detail&num=${board[i].num}">${board[i].subject}</a></td>
										<td width="40%" align="center">${board[i].bookname}</td>
										<td width="20%" align="center">${board[i].writer}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
							
						</c:choose>
						<tr>
							<td colspan="4" align="center"><c:choose>
									<c:when test="${ 1 eq page}">
									</c:when>
									<c:otherwise>
										<a href="/NotFound/main.do?current_page=${page-1}"
											class="btn btn-default">이전페이지</a>
									</c:otherwise>
								</c:choose> ${page} 
								<c:choose>
									<c:when test="${ page eq size}">
									</c:when>
									<c:otherwise>
										<a href="/NotFound/main.do?current_page=${page+1}"
											class="btn btn-default">다음페이지</a>
									</c:otherwise>
								</c:choose></td>
						</tr>

					</table>
				</div>
			</div>
			<hr>

		</div>
		<!--/center-->
	</div>
	<!-- 여기 까지 -->
	<div class="layer">
		<div class="bg"></div>
		<div id="layer2" class="pop-layer">
			<div class="pop-container">
				<div class="pop-conts">
					<form name="writeFrom" method="post" action="/NotFound/main.do">
						<!--enctype="multipart/form-data" 이게 뭐지-->

						<input type="hidden" name="main_action" value="write"> <input
							type="hidden" name="id" value="<%=session.getAttribute("id")%>">
						<input type="hidden" name="publisher" id="publisher"> <input
							type="hidden" name="publication_date" id="publication_date">
						<input type="hidden" name="book_img" id="book_img"> <input
							type="hidden" name="description" id="description">


						<table width="100%" border="1" align="center" cellspacing="0"
							cellpadding="3">
							<tr>
								<th scope="row" height="10%">제목</th>
								<td><input name="subject" id="subject" type="text"
									PLACEHOLDER="글 제목을 입력해주세요"></td>
							</tr>
							<tr>
								<th scope="row" height="10%">책 제목</th>
								<td><input type="text" PLACEHOLDER="제목을 입력해주세요"
									name="bookname" id="bookname"> <input type="button"
									value="검색" onclick="booksearch();" /></td>
							</tr>
							<tr>
								<th scope="row">저자</th>
								<td><input type="text" name="author" id="author"
									LACEHOLDER="작가를 입력해주세요"></input></td>
							</tr>
							<tr>
								<th scope="row">글 내용</th>
								<td><textarea name="contents" cols="45" rows="10"></textarea></td>
							</tr>
							<tr>
								<th colspan="2" scope="row"><input type="button" value="등록"
									onclick="writeCheck();"> <input type="button"
									value="취소" onclick="fadelayer();"></th>
							</tr>
						</table>
					</form>
					<!--// content-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>