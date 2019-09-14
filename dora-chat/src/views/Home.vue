<template>
  <div class="home">
    <button class="btn btn-primary">hi</button>
    <span>{{ status }}</span>
    <button @click="connect">Connect</button>

    <input type="text" v-model="ms" @keyup.enter="send" />
    <table border="1" cellspacing="0" cellpadding="5">
      <tr v-for="(m, i) in messages" :key="i">
        <td>{{ m }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import SockJs from "sockjs-client";
// import Stomp from '@stomp/stompjs'
import Stomp from "webstomp-client";
import { API_URL } from "../config/constant";

export default {
  name: "home",
  components: {
    HelloWorld
  },
  data() {
    return {
      stompClient: null,
      status: "",
      ms: "",
      messages: []
    };
  },
  created() {},
  methods: {
    send() {
      if (!this.stompClient) {
        this.status = "Error, connect socket first";
        return;
      }
      this.status = "";
      const ms = JSON.stringify({ name: this.ms });
      console.log("===================>", ms);
      this.stompClient.send("/app/hello", ms, {});
    },
    connect() {
      const socket = new SockJs(`${API_URL}/gs`);
      console.log("Socket init OK", socket);
      this.stompClient = Stomp.over(socket);
      console.log("stompClient init OK", this.stompClient);
      this.stompClient.connect({}, frame => {
        console.log("===================> Connected", frame);
        this.stompClient.subscribe(`/topic/test`, payload => {
          console.log(payload.body);
          this.messages.push(JSON.parse(payload.body).content);
        });
      });
    }
  }
};
</script>
