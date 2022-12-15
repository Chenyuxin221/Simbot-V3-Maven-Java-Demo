package com.huahua.demo.listener;

import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.FilterValue;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.event.EventResult;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.message.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * ClassName: MyListener
 *
 * @author 花云端
 * @description 监听示例
 * @date 2022-12-15 11:34
 */
@Component
public class MyListener {

    /**
     * 群消息监听
     * @param event 群消息事件
     * @param name 名字
     * @return EventResult 事件结果，可以控制事件流程
     * 如果不打算监听整个事件流程，可以返回void
     * 通过@Filter可以过滤消息事件,具体可翻看文档或代码注释
     * 多个@Filter代表触发任意一个即可
     * 以下例子为 该监听器只能被@bot且消息内容为我叫xx触发
     * 可使用{{xxx}}，用@FilterValue(”xxx“)取到想要的消息子串
     */
    @Listener
    @Filter(value = "我叫{{name}}",matchType = MatchType.REGEX_MATCHES,targets = @Filter.Targets(atBot = true))
    public EventResult myGroupMessageEvent(@NotNull GroupMessageEvent event, @FilterValue("name") String name){
        /*
         * 获取文本消息
         */
        var msgText = event.getMessageContent().getPlainText().trim();
        /*
         * 获取群对象
         */
        var group = event.getGroup();

        /*
         * 通过群对象获取群号
         */
        var id = group.getId();

        /*
         * 获取成员对象
         */
        var member = event.getAuthor();

        /*
         * 通过成员对象获取当前发言成员的QQ号码
         */
        var memberId = member.getId();

        /*
         * 获取消息构建器
         */
        var builder = new MessagesBuilder();

        /*
         * 构建at消息以及文本消息
         */
        var text = builder.at(member.getId())
                .text("你好,你说的话是：")
                .text(msgText)
                .build();

        /*
         * 回复此成员的消息
         */
        member.sendAsync(text);

        /*
         * 直接发送此消息
         */
//        group.sendAsync(text);

        /*
         * 返回truncate,截断后续监听函数
         */
        return EventResult.truncate();
    }

    /**
     * 好友消息监听
     * @param event 好友消息事件
     */
    @Listener
    public void myFriendListener(@NotNull FriendMessageEvent event){
        /*
         * 获取好友对象
         */
        final var friend = event.getFriend();

        /*
         * 获取好友ID
         */
        final var friendId = friend.getId();

        /*
         * 获取文本消息
         */
        final var msg = event.getMessageContent().getPlainText().trim();

        /*
         * 遍历消息内容
         */
        event.getMessageContent().getMessages().forEach(message -> {
            /*
             * 如果消息类型为image 则打印图片链接
             */
            if (message instanceof Image<?>){
                System.out.println(((Image<?>) message).getResource().getName());
            }
            /*
             * 如果消息类型为文本,则输出文本内容
             */
            if (message instanceof Text){
                friend.sendAsync(((Text) message).getText());
            }

        });
    }

}
