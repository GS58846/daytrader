<%@ page contentType="text/html;charset=UTF-8" language="java" %><%
  //Check if the user have rejected
  String error = request.getParameter("error");
  if ((null != error) && ("access_denied".equals(error.trim())))
  {
    HttpSession sess = request.getSession();
    sess.invalidate();
    response.sendRedirect(request.getContextPath());
  }

%>


<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
