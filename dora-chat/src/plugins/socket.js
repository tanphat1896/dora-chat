import SockJs from "sockjs-client";
import Stomp from "webstomp-client";
import { API_URL } from "../config/constant";

export default {
  data() {
    return {
      stomp: null,
      socket: null,
      connected: false
    };
  },
  created() {
    this.connect();
  },
  methods: {
    connect() {
      this.socket = new SockJs(`${API_URL}/ws-dora`);
      this.stomp = Stomp.over(this.socket);
      this.stomp.connect({}, this.onConnected);
    },
    onConnected() {
      console.log("Ws connected!!!");
      this.connected = true;
      this.joinRoom && this.joinRoom();
    }
  }
};
