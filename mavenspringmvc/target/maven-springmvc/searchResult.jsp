<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%--
  Created by IntelliJ IDEA.
  User: congshibo
  Date: 22/9/17
  Time: 12:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <title>Search Results | UNSWBook</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link href="style.css" type="text/css" rel="stylesheet">
</head>
<script type="text/javascript" src="jquery-easyui-v1.4.4/jquery.min.js"></script>

<script type="text/javascript">
    setInterval(function(){
        getMsgNum();
    },3000);

    function getMsgNum(){
        $.ajax({
            url:'PollingNotification',
            type:'post',
            dataType:'json',
            success:function(data){
                if(data && data.msgNum){
                    $("#notification").html("Unread notification("+data.msgNum+")");
                }else{
                    $("#notification").html("Notification");
                }
            }
        });
    }

</script>
<body>
  <nav>
    <div class="header-bar">
      <div class="container">
        <c:if test="${role=='user'}">
          <a href="mainpage.jsp" class="header-logo">UNSWBook</a>
        </c:if>
        <c:if test="${role=='admin'}">
          <a href="advancedSearch.jsp" class="header-logo">UNSWBook</a>
        </c:if>
        <button class="btn btn-default right-text logout-button" onclick="window.location.href('index.jsp')">Logout</button>
      </div>

    </div>
    <div class="header-strip">
    </div>
  </nav>

  <div id="content" class="container">
    <div class="row">
      <c:if test="${role=='user'}">
      <div class="col-sm-2">
          <a href="advancedSearch.jsp">Advanced Search</a>
          <a href="getMyMessage">My Message</a>
          <a href="changeDetail.jsp">Change My Details</a>
          <a href="getNotification" id="notification">Notifications</a>
          <a href="graphSearch.jsp">Graph Search</a>
          <a href="login.jsp">Log out</a>
      </div>
      </c:if>
      <div class="col-sm-9">
        <div class="well well-white">
          <h1>Users found: </h1>
          <c:forEach items="${userlist}" var="userList">
            <div class="list-group">
              <a href="showUserDetail?uid=${userList.id}" class="list-group-item">
                <h5 class="list-group-item-heading">Username: ${userList.name}</h5>
                <p class="list-group-item-text">User ID: ${userList.id}</p>
              </a>
            </div>
          </c:forEach>
        </div>
      </div>
    
    </div>
  </div>

  <footer>
    <p>COMP9321 Social Media Assignment</p><p class="right-text">UNSWBook</p>
  </footer>

</body>

</html>