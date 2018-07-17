# shield

## 前端防暴力破解的一个设计

传统的防范暴力破解的方法是在前端登录页面增加验证码, 虽然能有一定程度效果, 但是用户也跟着遭罪, 验证码越复杂, 用户登录的失败率越高


于是最近我想了一个新的设计, 前端在登录时采用解密的方式获取密钥, 把密钥与表单以前发往后端, 用密钥来代替验证码


具体细节如下

### 设计
- 用户在登录页面输完用户名密码, 点击登录
- js 向后端请求密文
- 后端生成一个```随机字符串```和一个指定范围内的```随机数```
- 正向拼接 随机字符串 和 随机字符串随机数的加密 得到密文```rstr+MD5(rstr+rint)```
- 反向拼接 得到 密钥 ```MD5(rint+rstr)```
```
    randomString = Utils.getUUID();
    randomNumber = Utils.randomInt(range);
    privateText =  randomString + Utils.md5(randomString+randomNumber);
    privateKey = Utils.md5(randomNumber+randomString);
```
- 将密文传给前端
- 前端通过循环破解随机数
```
    let randomString = result.substring(0, 32)
    let valueString = result.substring(32)
    let answerString
    for (let i = 0; i < range; i++) {
        let s = crypto.createHash("md5").update(randomString + i).digest('hex')
        if (s == valueString) {
            answerString = crypto.createHash("md5").update(i + randomString).digest('hex')
            break
        }
    }
```
- 把得到的密钥和表单一起传个后端
- 后端验证密钥的真假

### 测试
经过测试10000次内md5加密前端用时不超过300ms, 用户察觉不到, 但是暴力破解的难道确增加了几千倍, 这意味这本来一个小时能破解的网站, 现在可能要一年才能破解

### 优势
- 整个流程对后端带来的压力几乎为0
- 用户无需输入验证码
- 前端延时极小(对人来说)
- 对暴力破解影响极大
- 只需添加部分代码, 无需更改现有的代码
- 条件可控, 随机数的范围完全由后端决定