<template>
  <div class="modal-overlay" v-show="isVisible">
    <div class="modal-page">
      <div class="modal-header">
        <h3 class="modal-title">{{ title }}</h3>
        <button @click="closeModal">X</button>
      </div>
      <div class="modal-body">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Modal",
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
    title: {
      type: String,
      default: "Modal Title",
    },
  },
  methods: {
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-page {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 600px;
  height: 800px;
  max-width: 80%;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.modal-body {
  margin-top: 20px;
}
.modal-title {
  font-size: 30px;
  font-weight: bold;
  margin: 0 3px;
}
</style>
