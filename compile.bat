cd webapps

for /D %d in (*) do (
    cd %d
    echo %d
    cd WEB-INF
    for /R %I in (*.java) do (
        echo %I
        set c=%~dpI
        echo %c
        for /F "delims=" %A in ('echo %c') do ( set foldername=%~nxA )
        echo %foldername%
    )
)