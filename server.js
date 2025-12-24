const express = require('express');
const path = require('path');
const app = express();
const port = 8000;

// 设置静态文件目录
app.use(express.static(path.join(__dirname, 'src', 'main', 'resources', 'front')));

// API路由模拟 - 用户数据
// 获取所有用户
app.get('/users', (req, res) => {
  // 模拟用户数据
  const users = [
    {
      userId: 1,
      username: 'admin',
      password: 'admin123',
      nickname: '管理员',
      email: 'admin@example.com',
      phone: '13800138000',
      bio: '系统管理员',
      moviePreferences: '动作,科幻',
      avatarBase64: null
    },
    {
      userId: 2,
      username: 'user1',
      password: 'password123',
      nickname: '用户1',
      email: 'user1@example.com',
      phone: '13800138001',
      bio: '普通用户',
      moviePreferences: '喜剧,爱情',
      avatarBase64: null
    },
    {
      userId: 3,
      username: 'user2',
      password: 'user2pass',
      nickname: '用户2',
      email: 'user2@example.com',
      phone: '13800138002',
      bio: 'VIP用户',
      moviePreferences: '悬疑,惊悚',
      avatarBase64: null
    }
  ];
  
  res.json(users);
});

// 更新用户信息
app.put('/users/:id', (req, res) => {
  const userId = parseInt(req.params.id);
  const updatedUserData = req.body;
  
  // 模拟用户数据更新
  const users = [
    {
      userId: 1,
      username: 'admin',
      password: 'admin123',
      nickname: '管理员',
      email: 'admin@example.com',
      phone: '13800138000',
      bio: '系统管理员',
      moviePreferences: '动作,科幻',
      avatarBase64: null
    },
    {
      userId: 2,
      username: 'user1',
      password: 'password123',
      nickname: '用户1',
      email: 'user1@example.com',
      phone: '13800138001',
      bio: '普通用户',
      moviePreferences: '喜剧,爱情',
      avatarBase64: null
    },
    {
      userId: 3,
      username: 'user2',
      password: 'user2pass',
      nickname: '用户2',
      email: 'user2@example.com',
      phone: '13800138002',
      bio: 'VIP用户',
      moviePreferences: '悬疑,惊悚',
      avatarBase64: null
    }
  ];
  
  // 查找要更新的用户
  const userIndex = users.findIndex(user => user.userId === userId);
  
  if (userIndex === -1) {
    return res.status(404).json({ error: '用户未找到' });
  }
  
  // 更新用户数据
  users[userIndex] = {
    ...users[userIndex],
    ...updatedUserData,
    userId: userId // 确保userId不被更改
  };
  
  res.json({ success: true, user: users[userIndex] });
});

// API路由模拟 - 电影数据
app.get('/movies', (req, res) => {
  // 模拟电影数据
  const movies = [
    {
      movieId: 1,
      title: '复仇者联盟',
      description: '超级英雄团队联手对抗外星入侵者',
      releaseDate: '2012-05-04',
      duration: 143,
      language: '英语',
      country: '美国',
      imdbRating: 8.0,
      director: '乔斯·韦登',
      actors: '小罗伯特·唐尼, 克里斯·埃文斯, 斯嘉丽·约翰逊',
      genres: '动作,科幻,冒险',
      posterBase64: null
    },
    {
      movieId: 2,
      title: '阿凡达',
      description: '人类在潘多拉星球上的科幻冒险故事',
      releaseDate: '2009-12-18',
      duration: 162,
      language: '英语',
      country: '美国',
      imdbRating: 7.8,
      director: '詹姆斯·卡梅隆',
      actors: '萨姆·沃辛顿, 佐伊·索尔达娜, 西格妮·韦弗',
      genres: '动作,科幻,冒险',
      posterBase64: null
    },
    {
      movieId: 3,
      title: '泰坦尼克号',
      description: '豪华邮轮沉没的爱情悲剧',
      releaseDate: '1997-12-19',
      duration: 194,
      language: '英语',
      country: '美国',
      imdbRating: 7.8,
      director: '詹姆斯·卡梅隆',
      actors: '莱昂纳多·迪卡普里奥, 凯特·温斯莱特',
      genres: '剧情,爱情',
      posterBase64: null
    },
    {
      movieId: 4,
      title: '战狼2',
      description: '特种兵海外救援行动',
      releaseDate: '2017-07-27',
      duration: 123,
      language: '汉语',
      country: '中国',
      imdbRating: 6.2,
      director: '吴京',
      actors: '吴京, 弗兰克·格里罗, 卢靖姗',
      genres: '动作,战争',
      posterBase64: null
    },
    {
      movieId: 5,
      title: '千与千寻',
      description: '小女孩在神秘世界的成长冒险',
      releaseDate: '2001-07-20',
      duration: 125,
      language: '日语',
      country: '日本',
      imdbRating: 8.6,
      director: '宫崎骏',
      actors: '柊瑠美, 入野自由, 夏木真理',
      genres: '动画,奇幻',
      posterBase64: null
    },
    {
      movieId: 6,
      title: '寄生虫',
      description: '贫富家庭之间的黑色幽默故事',
      releaseDate: '2019-05-30',
      duration: 132,
      language: '韩语',
      country: '韩国',
      imdbRating: 8.5,
      director: '奉俊昊',
      actors: '宋康昊, 李善均, 赵汝贞',
      genres: '剧情,喜剧,惊悚',
      posterBase64: null
    },
    {
      movieId: 7,
      title: '辛德勒的名单',
      description: '二战期间德国商人的救赎故事',
      releaseDate: '1993-12-15',
      duration: 195,
      language: '英语',
      country: '美国',
      imdbRating: 9.0,
      director: '史蒂文·斯皮尔伯格',
      actors: '连姆·尼森, 本·金斯利, 拉尔夫·费因斯',
      genres: '剧情,历史,战争',
      posterBase64: null
    },
    {
      movieId: 8,
      title: '霸王别姬',
      description: '两位京剧演员跨越半个世纪的悲欢离合',
      releaseDate: '1993-01-01',
      duration: 171,
      language: '汉语',
      country: '中国',
      imdbRating: 8.1,
      director: '陈凯歌',
      actors: '张国荣, 张丰毅, 巩俐',
      genres: '剧情,爱情',
      posterBase64: null
    },
    {
      movieId: 9,
      title: '肖申克的救赎',
      description: '银行家在监狱中的希望与救赎',
      releaseDate: '1994-09-23',
      duration: 142,
      language: '英语',
      country: '美国',
      imdbRating: 9.3,
      director: '弗兰克·德拉邦特',
      actors: '蒂姆·罗宾斯, 摩根·弗里曼',
      genres: '剧情',
      posterBase64: null
    },
    {
      movieId: 10,
      title: '美丽人生',
      description: '父亲在集中营中为儿子编织美丽谎言',
      releaseDate: '1997-12-20',
      duration: 116,
      language: '意大利语',
      country: '意大利',
      imdbRating: 8.6,
      director: '罗伯托·贝尼尼',
      actors: '罗伯托·贝尼尼, 尼可莱塔·布拉斯基',
      genres: '剧情,喜剧',
      posterBase64: null
    },
    {
      movieId: 11,
      title: '罗马假日',
      description: '公主与记者的罗马一日游',
      releaseDate: '1953-09-02',
      duration: 118,
      language: '英语',
      country: '美国',
      imdbRating: 8.0,
      director: '威廉·惠勒',
      actors: '奥黛丽·赫本, 格利高里·派克',
      genres: '剧情,喜剧,爱情',
      posterBase64: null
    },
    {
      movieId: 12,
      title: '卧虎藏龙',
      description: '江湖侠客的爱恨情仇',
      releaseDate: '2000-05-10',
      duration: 120,
      language: '汉语',
      country: '中国',
      imdbRating: 7.8,
      director: '李安',
      actors: '周润发, 杨紫琼, 章子怡',
      genres: '动作,武侠,剧情',
      posterBase64: null
    },
    {
      movieId: 13,
      title: '机器人总动员',
      description: '未来世界中机器人的爱情故事',
      releaseDate: '2008-06-27',
      duration: 98,
      language: '英语',
      country: '美国',
      imdbRating: 8.4,
      director: '安德鲁·斯坦顿',
      actors: '本·贝尔特, 艾丽莎·奈特',
      genres: '动画,科幻',
      posterBase64: null
    },
    {
      movieId: 14,
      title: '你的名字',
      description: '男女主角身体互换的奇幻爱情',
      releaseDate: '2016-08-26',
      duration: 106,
      language: '日语',
      country: '日本',
      imdbRating: 8.4,
      director: '新海诚',
      actors: '神木隆之介, 上白石萌音',
      genres: '动画,剧情,爱情',
      posterBase64: null
    },
    {
      movieId: 15,
      title: '摔跤吧！爸爸',
      description: '父亲培养女儿成为摔跤冠军的故事',
      releaseDate: '2016-12-23',
      duration: 161,
      language: '印地语',
      country: '印度',
      imdbRating: 8.4,
      director: '涅提·蒂瓦里',
      actors: '阿米尔·汗, 法缇玛·萨那·纱卡',
      genres: '剧情,传记,运动',
      posterBase64: null
    }
  ];
  
  res.json(movies);
});

// API路由模拟 - 收藏数据
app.get('/favorites', (req, res) => {
  // 模拟收藏数据
  const favorites = [
    {
      id: 1,
      userId: 1,
      movieId: 1,
      createdAt: '2023-01-15T10:30:00'
    },
    {
      id: 2,
      userId: 2,
      movieId: 1,
      createdAt: '2023-02-20T14:45:00'
    },
    {
      id: 3,
      userId: 3,
      movieId: 2,
      createdAt: '2023-03-10T09:15:00'
    }
  ];
  
  res.json(favorites);
});

// API路由模拟 - 评论数据
app.get('/reviews', (req, res) => {
  // 模拟评论数据
  const reviews = [
    {
      reviewId: 1,
      movieId: 1,
      sessionId: 1,
      userId: 1,
      content: '非常精彩的超级英雄电影，特效震撼！',
      reviewTime: '2023-01-15T11:30:00'
    },
    {
      reviewId: 2,
      movieId: 1,
      sessionId: 1,
      userId: 2,
      content: '剧情紧凑，角色塑造很好',
      reviewTime: '2023-02-20T15:45:00'
    },
    {
      reviewId: 3,
      movieId: 2,
      sessionId: 2,
      userId: 3,
      content: '视觉效果令人惊叹，故事情节引人入胜',
      reviewTime: '2023-03-10T10:15:00'
    }
  ];
  
  res.json(reviews);
});

// AI电影推荐接口
app.get('/AI', (req, res) => {
  const userPreferences = req.query.data || '动作,科幻';
  
  // 模拟AI推荐结果
  const mockRecommendations = `基于您的偏好"${userPreferences}"，我们为您精心推荐以下电影：

🎬 **个性化推荐列表**：

1. 🔥《肖申克的救赎》- 一部关于希望与自由的经典剧情片，IMDb评分9.3
2. 👾《阿凡达》- 视觉震撼的科幻冒险电影，詹姆斯·卡梅隆导演作品
3. 💕《泰坦尼克号》- 永恒的爱情故事，莱昂纳多·迪卡普里奥主演
4. 💥《复仇者联盟》- 超级英雄集结的精彩动作片，漫威宇宙经典之作
5. 🌟《千与千寻》- 充满想象力的动画奇幻作品，宫崎骏代表作

📋 **推荐理由**：
- 这些电影涵盖了您喜欢的多种类型：剧情、科幻、爱情、动作和动画
- 每部电影都是各自领域的经典之作，具有很高的艺术价值和观赏性
- 根据您的偏好，我们特别推荐了视觉效果出色的科幻片和情感丰富的剧情片

📌 **观影建议**：
建议您按照推荐顺序观看，从剧情片开始，逐步体验不同类型电影的魅力。每部电影都有其独特的艺术价值，希望您能享受这次观影之旅！`;
  
  // 模拟AI处理时间
  setTimeout(() => {
    res.send(mockRecommendations);
  }, 2000);
});

// 启动服务器
app.listen(port, () => {
  console.log(`服务器正在运行在 http://localhost:${port}`);
  console.log(`请在浏览器中访问 http://localhost:${port}/admin_dashboard.html`);
});