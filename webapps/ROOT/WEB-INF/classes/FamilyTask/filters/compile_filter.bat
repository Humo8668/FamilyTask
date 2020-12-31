for /f %%f in ('dir /s /b *.java') do ( javac --release 8 -cp "D:\Projects\Home\FamilyTask\webapps\ROOT\WEB-INF\classes;D:\Projects\Home\FamilyTask\lib\servlet-api.jar" %%f)
echo "Filters compiled"
exit