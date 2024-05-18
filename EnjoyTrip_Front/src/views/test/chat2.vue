<template>
  <div
    class="chat-container"
    ref="chatContainer"
    @mousedown="startDrag"
    :style="{ left: `${position.x}px`, top: `${position.y}px` }"
  >
    <div class="chat-header">채팅</div>
    <div class="chat-body">
      <p>친구와의 대화 내용...</p>
    </div>
    <div class="chat-footer">
      <input type="text" placeholder="메시지를 입력하세요" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      position: { x: window.innerWidth - 320, y: window.innerHeight - 450 },
      dragging: false,
      offset: { x: 0, y: 0 },
    };
  },
  methods: {
    startDrag(e) {
      if (e.target.classList.contains("chat-header")) {
        this.dragging = true;
        this.offset.x =
          e.clientX - this.$refs.chatContainer.getBoundingClientRect().left;
        this.offset.y =
          e.clientY - this.$refs.chatContainer.getBoundingClientRect().top;
        document.addEventListener("mousemove", this.onDrag);
        document.addEventListener("mouseup", this.stopDrag);
      }
    },
    onDrag(e) {
      if (this.dragging) {
        this.position.x = e.clientX - this.offset.x;
        this.position.y = e.clientY - this.offset.y;
      }
    },
    stopDrag() {
      this.dragging = false;
      document.removeEventListener("mousemove", this.onDrag);
      document.removeEventListener("mouseup", this.stopDrag);
    },
  },
};
</script>

<style scoped>
.chat-container {
  width: 300px;
  height: 400px;
  position: fixed;
  border: 1px solid #ccc;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  z-index: 1000;
}
.chat-header {
  background-color: #0078d4;
  color: #fff;
  padding: 10px;
  text-align: center;
  cursor: move;
}
.chat-body {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}
.chat-footer {
  padding: 10px;
  border-top: 1px solid #ccc;
}
.chat-footer input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>
