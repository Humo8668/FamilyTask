<%@ page session="false" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v4.1.1">
    <title>Signin Template · Bootstrap</title>
    <title>Login page</title>
    <link href="public/css/bootstrap.min.css" rel="stylesheet">
    <link href="public/css/signin_signup_style.css" rel="stylesheet">

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
    <form class="form-signin" method="POST" action="login()">
        <h1 class="h3 mb-3 font-weight-normal">Авторизуйтесь</h1>
        <label for="name" class="sr-only">Имя пользователя</label>
        <input type="text" id="name" name="name" class="form-control" placeholder="Имя пользователя" required autofocus>
        <label for="password" class="sr-only">Пароль</label>
        <input type="passwords" id="password" class="form-control" placeholder="Пароль" required>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> Запомнить меня
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
        <br>
        <h6 class="font-weight-normal"> Нет аккаунта? <a href="#"> Зарегистрируйтесь :-)</a> </h6>
    </form>
</body>

<script>
    function login() {
        
    }
</script>
</html>