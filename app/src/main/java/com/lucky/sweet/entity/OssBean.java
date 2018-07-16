package com.lucky.sweet.entity;

/**
 * Created by chn on 2018/1/18.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class OssBean {

    /**
     * RequestId : 102903BB-7408-444B-97E4-422A2A95EF27
     * AssumedRoleUser : {"AssumedRoleId":"336777017757943326:bbb","Arn":"acs:ram::1321290185118957:role/aliyunosstokengeneratorrole/bbb"}
     * Credentials : {"AccessKeySecret":"D1bxrTCww8Cpt4CdQ7HwS3o5upYjnC6uzYdm9BJEjDJZ","AccessKeyId":"STS.FentPfXNqzLuJKR1cDFT84bSR","Expiration":"2018-01-18T10:21:12Z","SecurityToken":"CAIS/QF1q6Ft5B2yfSjIp6bbP+rStZFQzY6eSG3j1WMRStsU26f4sDz2IHtKe3ZvAekZsfkwlWxT7fwclqp5QZUdtOoJwzM0vPpt6gqET9frma7ctM4p6vCMHWyUFGSIvqv7aPn4S9XwY+qkb0u++AZ43br9c0fJPTXnS+rr76RqddMKRAK1QCNbDdNNXGtYpdQdKGHaOITGUHeooBKJVxAx4Fsk0DMisP3vk5DD0HeE0g2mkN1yjp/qP52pY/NrOJpCSNqv1IR0DPGajnEPtEATq/gr0/0Yomyd4MvuCl1Q8giANPHP7tpsIQl2a643AadYq+Lmkvl1qmkSey1SFdInGoABtRIjpGhN9XXzP6vmCXCiCEi20Xi4liUlpE6VCKsSTYOioDFO9n+Idu+oa+HcP57ln3HvdeoH3cUE9sxNn98TuMascNhdMlmUN60aVjWQJDKS8wRwpViKNhUtObF+QFrZn5k/XLQxm3lerUJM/DNcunWd2AL1udM5O1qFVBNwdgg="}
     */

    private String RequestId;
    private AssumedRoleUserBean AssumedRoleUser;
    private CredentialsBean Credentials;

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public AssumedRoleUserBean getAssumedRoleUser() {
        return AssumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUserBean AssumedRoleUser) {
        this.AssumedRoleUser = AssumedRoleUser;
    }

    public CredentialsBean getCredentials() {
        return Credentials;
    }

    public void setCredentials(CredentialsBean Credentials) {
        this.Credentials = Credentials;
    }

    public static class AssumedRoleUserBean {
        /**
         * AssumedRoleId : 336777017757943326:bbb
         * Arn : acs:ram::1321290185118957:role/aliyunosstokengeneratorrole/bbb
         */

        private String AssumedRoleId;
        private String Arn;

        public String getAssumedRoleId() {
            return AssumedRoleId;
        }

        public void setAssumedRoleId(String AssumedRoleId) {
            this.AssumedRoleId = AssumedRoleId;
        }

        public String getArn() {
            return Arn;
        }

        public void setArn(String Arn) {
            this.Arn = Arn;
        }
    }

    public static class CredentialsBean {
        /**
         * AccessKeySecret : D1bxrTCww8Cpt4CdQ7HwS3o5upYjnC6uzYdm9BJEjDJZ
         * AccessKeyId : STS.FentPfXNqzLuJKR1cDFT84bSR
         * Expiration : 2018-01-18T10:21:12Z
         * SecurityToken : CAIS/QF1q6Ft5B2yfSjIp6bbP+rStZFQzY6eSG3j1WMRStsU26f4sDz2IHtKe3ZvAekZsfkwlWxT7fwclqp5QZUdtOoJwzM0vPpt6gqET9frma7ctM4p6vCMHWyUFGSIvqv7aPn4S9XwY+qkb0u++AZ43br9c0fJPTXnS+rr76RqddMKRAK1QCNbDdNNXGtYpdQdKGHaOITGUHeooBKJVxAx4Fsk0DMisP3vk5DD0HeE0g2mkN1yjp/qP52pY/NrOJpCSNqv1IR0DPGajnEPtEATq/gr0/0Yomyd4MvuCl1Q8giANPHP7tpsIQl2a643AadYq+Lmkvl1qmkSey1SFdInGoABtRIjpGhN9XXzP6vmCXCiCEi20Xi4liUlpE6VCKsSTYOioDFO9n+Idu+oa+HcP57ln3HvdeoH3cUE9sxNn98TuMascNhdMlmUN60aVjWQJDKS8wRwpViKNhUtObF+QFrZn5k/XLQxm3lerUJM/DNcunWd2AL1udM5O1qFVBNwdgg=
         */

        private String AccessKeySecret;
        private String AccessKeyId;
        private String Expiration;
        private String SecurityToken;

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String AccessKeySecret) {
            this.AccessKeySecret = AccessKeySecret;
        }

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String AccessKeyId) {
            this.AccessKeyId = AccessKeyId;
        }

        public String getExpiration() {
            return Expiration;
        }

        public void setExpiration(String Expiration) {
            this.Expiration = Expiration;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String SecurityToken) {
            this.SecurityToken = SecurityToken;
        }
    }
}
