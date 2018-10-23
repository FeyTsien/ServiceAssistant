package com.dt.serviceassistant.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/7/2.
 */

public class AppBean implements Serializable {



    /**
     * status : 1
     * class : 1
     * expire_in : 2018-10-15 14:44:39
     * money : 0.00
     * usedTraffic : 216.36M
     * Traffic : 60G
     * all : -156
     * nodes : [{"name":"hkc","server":"hkc.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"hkd","server":"hkd.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"Netflix-俄罗斯","server":"ru.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"VIP试用","server":"vip.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"东京","server":"tokyo.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"台湾","server":"tw.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"新加坡","server":"sg.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"新加坡-100M","server":"vn.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"游戏节点-香港","server":"game-hk.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"简体中文-Netflix","server":"hkbn.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"香港Netflix-动态首选","server":"hkt.ojbk.cool","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""},{"name":"高速视频-日本","server":"us.ssr.guru","server_port":3783,"method":"rc4-md5","obfs":"plain","obfsparam":"jd.hk","password":"tKu4h5","group":"MangosteenCloud","protocol":"origin","protoparam":"","protocolparam":""}]
     * link : https://mangosteen.cloud/link/8fC3Df2I2Dta5Nyh?mu=0
     */

    //请求后台返回的状态码
    private int status;
    //请求后台返回的的消息内容
    private String reason;
    //等级
    private int level;
    //时间
    private String expire_in;
    //余额
    private String money;
    //用户使用流量
    private String usedTraffic;
    //总计流量
    private String Traffic;
    private int all;
    //订阅链接
    private String link;
    //节点列表
    private List<NodeBean> nodes;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUsedTraffic() {
        return usedTraffic;
    }

    public void setUsedTraffic(String usedTraffic) {
        this.usedTraffic = usedTraffic;
    }

    public String getTraffic() {
        return Traffic;
    }

    public void setTraffic(String Traffic) {
        this.Traffic = Traffic;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<NodeBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeBean> nodes) {
        this.nodes = nodes;
    }

    public static class NodeBean implements Serializable {
        /**
         * name : hkc
         * server : hkc.ssr.guru
         * server_port : 3783
         * method : rc4-md5
         * obfs : plain
         * obfsparam : jd.hk
         * password : tKu4h5
         * group : MangosteenCloud
         * protocol : origin
         * protoparam :
         * protocolparam :
         */

        private String name;
        private String server;
        private int server_port;
        private String method;
        private String obfs;
        private String obfsparam;
        private String password;
        private String group;
        private String protocol;
        private String protoparam;
        private String protocolparam;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public int getServer_port() {
            return server_port;
        }

        public void setServer_port(int server_port) {
            this.server_port = server_port;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getObfs() {
            return obfs;
        }

        public void setObfs(String obfs) {
            this.obfs = obfs;
        }

        public String getObfsparam() {
            return obfsparam;
        }

        public void setObfsparam(String obfsparam) {
            this.obfsparam = obfsparam;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getProtoparam() {
            return protoparam;
        }

        public void setProtoparam(String protoparam) {
            this.protoparam = protoparam;
        }

        public String getProtocolparam() {
            return protocolparam;
        }

        public void setProtocolparam(String protocolparam) {
            this.protocolparam = protocolparam;
        }
    }
}
