set c=%cd%
for /f %%f in ('dir /b /s .\FamilyTask\*.bat') do (
    cd %%~dpf
    start %%f -wait
)
cd %c%