export default {
    login(form) {
        return new Promise((resolve, reject) => {
            LoginApi.ciphertext().then(result => {
                let randomString = result.substring(0, 32)
                let valueString = result.substring(32)
                let answerString
                for (let i = 0; i < 10000; i++) {
                    let s = crypto.createHash("md5").update(randomString + i).digest('hex')
                    if (s == valueString) {
                        answerString = crypto.createHash("md5").update(i + randomString).digest('hex')
                        break
                    }
                }
                form.ciphertext = answerString
                LoginApi.login(form).then((result) => {
                    resolve(result)
                }).catch(() => {
                    reject('error')
                })
            }).catch(() => {
                reject('error')
            })
        })
    }
}