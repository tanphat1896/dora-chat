<template>
  <div>
    <div class="main-bg"></div>
    <div v-if="token && connected" class="ui container">
      <div class="ui blue raised segment chatbox">
        <h1 class="ui dividing header">Chat box</h1>
        <div class="chatbox__banner"></div>

        <div ref="boxmessage" class="ui comments chatbox__message">
          <transition-group name="list" tag="p">
            <div
              v-for="(msg, i) in messages"
              class="comment"
              :key="`${i}${msg.sender}`"
            >
              <a class="avatar">
                <img :src="msg.avt" />
              </a>
              <div class="content">
                <a class="author">{{ msg.sender }}</a>
                <div class="metadata">
                  <span class="date">{{ msg.date }}</span>
                </div>
                <div
                  class="text"
                  v-if="msg.content === 'Vừa tham gia phòng chat'"
                >
                  <i>{{ msg.content }}</i>
                </div>
                <div v-else class="text">{{ msg.content }}</div>
                <!-- <div class="actions">
                  <a class="reply">Reply</a>
                </div> -->
              </div>
            </div>
          </transition-group>
        </div>

        <div class="mt-2 ui icon input fluid chatbox__input">
          <input
            type="text"
            placeholder="Nhập tin nhắn của bạn..."
            @keyup.enter="sendMessage"
            v-model="msg"
          />
          <i
            class="inverted blue circular location arrow link icon"
            @click="sendMessage"
          ></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import socket from "../plugins/socket";
import mm from 'moment'
const moment = new mm()
export default {
  mixins: [socket],
  data() {
    return {
      messages: [],
      msg: ""
    };
  },
  computed: {
    ...mapState(["name", "avt", "token"])
  },
  created() {
    if (!this.token) {
      return this.$router.push({ name: "SignIn" });
    }
    this.connect(this.token);
  },
  methods: {
    joinRoom() {
      console.log(this.connected);
      if (!this.connected) return;
      this.stomp.subscribe("/topic/public", this.onNewMessage);
      console.log(this.name, this.avt);
      this.stomp.send(
        "/app/join",
        JSON.stringify({
          sender: this.name,
          avt: this.avt,
          content: "Vừa tham gia phòng chat",
          date: moment.format('YYYY-MM-DD HH:mm:ss')
        }),
        {}
      );
    },
    sendMessage() {
      if (this.msg.trim() === '') return
      this.stomp.send(
        "/app/chat",
        JSON.stringify({
          sender: this.name,
          avt: this.avt,
          content: this.msg,
          date: moment.format('YYYY-MM-DD HH:mm:ss')
        }),
        {}
      );
      this.msg = "";
    },
    onNewMessage(payload) {
      const message = JSON.parse(payload.body);
      this.messages.push(message);
      this.$nextTick(() => {
        this.$refs.boxmessage &&
          (this.$refs.boxmessage.scrollTop = this.$refs.boxmessage.scrollHeight);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/main.scss";
</style>
<style lang="scss" scoped>
.list-item {
  display: inline-block;
  margin-right: 10px;
}
.list-enter-active,
.list-leave-active {
  transition: all 0.5s;
}
.list-enter, .list-leave-to /* .list-leave-active below version 2.1.8 */ {
  opacity: 0;
  transform: translateY(30px);
}
</style>
