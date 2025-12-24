@echo off
echo 正在检查Node.js环境...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo 未检测到Node.js环境，请先安装Node.js
    pause
    exit /b
)

echo 正在检查npm...
npm --version >nul 2>&1
if %errorlevel% neq 0 (
    echo 未检测到npm，请确保Node.js安装完整
    pause
    exit /b
)

echo 正在安装依赖...
npm install

echo 正在启动服务器...
echo 请在浏览器中访问 http://localhost:8000/admin_dashboard.html
npm start