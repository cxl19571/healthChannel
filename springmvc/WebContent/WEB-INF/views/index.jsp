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
    
       $(function(){
    	   $.get("domain/test2.top",function(data){
    		   
    		   var tt=data['list'][0];
   // 		   for(var i=0;i<tt.length;i++){
   // 			   
   // 			   var name=tt[i].userName;
   // 			   var age=tt[i].age;
    //			   
    //			   var tr="<tr><td>"+name+"</td><td>"+age+"</td></tr>";
    			   
    //			  $("table").append(tr)
    //			   console.info(data['list'][1].userName);
    //		   }
    		 
            $.each(tt,function(index,element){
            	
            	$("font").each(function(i){
            		
            		$(this).text(element)
            	});
            	console.info(index,element);
            });
    		   
    	   });
       });
    </script>
    </head>
    <body>
   
     this is myPage!!!!  
     
     <div>
        <label>名字:</label><font color="red"></font>
        <label>年龄:</label><font color="grey"></font>
     </div>
     
    </body>
</html>