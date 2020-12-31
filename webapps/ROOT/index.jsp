<%@ page session="true" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login page</title>
    <link href="/public/css/bootstrap.min.css" rel="stylesheet">
    <link href="/public/css/signin_signup_style.css" rel="stylesheet">
    <script src="/public/js/bootstrap.min.js"></script>
    <script src="/public/js/jquery-3.5.1.min.js"></script>
    <script src="/public/js/helper.js"></script>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body class="text-center">
    <form class="form-signin" name='login-form' method="POST" onsubmit="event.preventDefault(); login('login-form')">
        <h1 class="h3 mb-3 font-weight-normal">Авторизуйтесь</h1>
        <label for="name" class="sr-only">Имя пользователя</label>
        <input type="text" id="name" name="name" class="form-control" placeholder="Имя пользователя" required autofocus>
        <label for="password" class="sr-only">Пароль</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Пароль" required>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" id="remember" name="remember" value="remember-me"> Запомнить меня
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        <br>
        <h6 class="font-weight-normal"> Нет аккаунта? <a href="#"> Зарегистрируйтесь :-)</a> </h6>
    </form>
</body>

<script>
    function login(loginFormName)
    {
        var name = $("input#name").val();
        var password = $("input#password").val();
        var remember = $("input#remember").is(":checked");
        
        $.ajax({
          url: '/',
          method: 'post',
          headers:{
            "name" : name,
            "password" : password,
            "remember" : remember
          },
          settings: {
            async: true,
            success: function(data, textStatus, jqXHR)
            {
              alert("success");
            },
            error: function(jqXHR, textStatus, errorThrown)
            {
              alert(errorThrown);
            }
          }
        });
    }
</script>
</html>