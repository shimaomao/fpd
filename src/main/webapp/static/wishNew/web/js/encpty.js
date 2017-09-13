function encryptPwd(pwd,mod,exp){
    var pwdEnc;
    try{
        var rsa = new RSAKey();
        rsa.setPublic(mod, exp);
        var res = rsa.encrypt(pwd);
        pwdEnc = hex2b64(res);
    } catch(e) {
        console.log(e);
    }
    return pwdEnc;
}