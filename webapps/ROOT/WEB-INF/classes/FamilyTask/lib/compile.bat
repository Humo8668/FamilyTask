for /f %%f in ('dir /s /b *.java') do (
    javac --release 8 -cp "D:\Projects\Home\FamilyTask\webapps\ROOT\WEB-INF\classes\" %%f
)
echo "libs compiled"
exit