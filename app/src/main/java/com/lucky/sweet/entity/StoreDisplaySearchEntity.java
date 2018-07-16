package com.lucky.sweet.entity;

import java.util.List;

/**
 * Created by chn on 2018/1/29.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class StoreDisplaySearchEntity {
    /**
     * circle_list : {"select":[{"name":"万达商业区","list":["聚餐宴请 ","真人CS","KTV ","轰趴馆"]},{"name":"旅顺商业区","list":["聚餐宴请 ","KTV ","棋牌室"]},{"name":"软件学院","list":["KTV "]},{"name":"hahaha","list":["酒吧","更多休闲娱乐","棋牌室"]}],"order":["最新收录","消费最低(未写)"]}
     */

    private CircleListBean circle_list;

    public CircleListBean getCircle_list() {
        return circle_list;
    }

    public void setCircle_list(CircleListBean circle_list) {
        this.circle_list = circle_list;
    }

    public static class CircleListBean {
        private List<SelectBean> select;
        private List<String> order;

        public List<SelectBean> getSelect() {
            return select;
        }

        public void setSelect(List<SelectBean> select) {
            this.select = select;
        }

        public List<String> getOrder() {
            return order;
        }

        public void setOrder(List<String> order) {
            this.order = order;
        }

        public static class SelectBean {
            /**
             * name : 万达商业区
             * list : ["聚餐宴请 ","真人CS","KTV ","轰趴馆"]
             */

            private String name;
            private List<String> list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getList() {
                return list;
            }

            public void setList(List<String> list) {
                this.list = list;
            }
        }
    }
}
