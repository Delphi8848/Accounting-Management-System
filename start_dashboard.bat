@echo off
echo 正在启动Admin Dashboard测试服务器...
echo 请在浏览器中访问 http://localhost:8000/admin_dashboard.html
cd /d "C:\Users\biyua\Desktop\New folder (5)\第二份作业\高校社团管理系统\src\main\resources\front"
python -m http.server 8000
pause