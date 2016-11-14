<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <base href="<%=basePath%>">
    
    <script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
    
    <script type="text/javascript">
    
    </script>
    </head>
    <body>
   
     this is myPage!!!!  
     
     <div>
        <form enctype="Multipart/form-data">
        
              名称:<input type="text" name="userName"/>
              年龄:<input type="text" name="age"/>
             <input type="file" name="file"/>
            <input type="submit" value="提交" onclick="aa()"/>
        </form>
     </div>
     
    </body>
</html>