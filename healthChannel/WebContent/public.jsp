<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公共jsp</title>

<base href="<%=basePath%>">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入jquery -->
<script type="text/javascript" src="scripts/jquery-2.2.3.min.js"></script>
<!-- 引入easyui核心js -->
<script type="text/javascript" src="scripts/jquery.easyui.min.js"></script>
<!-- 引入highcharts核心js -->
<script type="text/javascript" src="scripts/highcharts.js"></script>
<!-- 引入bootstrap核心js -->
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<!-- 引入bootstrap核心css -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- 引入easyui核心css -->
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<link rel="stylesheet" type="text/css" href="css/demo.css">

</head>
<body>

</body>
</html>