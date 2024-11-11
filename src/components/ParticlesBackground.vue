<template>
  <canvas ref="canvas" class="particles"></canvas>
</template>

<script setup>
import { onMounted, ref } from "vue";

const canvas = ref(null);

onMounted(() => {
  const ctx = canvas.value.getContext("2d");
  const particles = [];
  const numParticles = 150; // 增加粒子数量

  canvas.value.width = window.innerWidth;
  canvas.value.height = window.innerHeight;

  // 初始化粒子
  for (let i = 0; i < numParticles; i++) {
    particles.push({
      x: Math.random() * canvas.value.width,
      y: Math.random() * canvas.value.height,
      vx: (Math.random() - 0.5) * 1.5,
      vy: (Math.random() - 0.5) * 1.5,
      radius: 2,
    });
  }

  const draw = () => {
    ctx.clearRect(0, 0, canvas.value.width, canvas.value.height);

    // 画粒子
    particles.forEach((p) => {
      ctx.fillStyle = "rgba(0, 0, 0, 0.7)";
      ctx.beginPath();
      ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
      ctx.fill();
    });

    // 画连接线和三角形
    for (let i = 0; i < particles.length; i++) {
      for (let j = i + 1; j < particles.length; j++) {
        const dist = Math.hypot(
          particles[i].x - particles[j].x,
          particles[i].y - particles[j].y
        );
        if (dist < 150) {
          // 增加连接线的最大距离
          ctx.strokeStyle = `rgba(0, 0, 0, ${1 - dist / 150})`;
          ctx.lineWidth = 0.5;
          ctx.beginPath();
          ctx.moveTo(particles[i].x, particles[i].y);
          ctx.lineTo(particles[j].x, particles[j].y);
          ctx.stroke();
        }

        // 添加三角形效果
        for (let k = j + 1; k < particles.length; k++) {
          const dist2 = Math.hypot(
            particles[i].x - particles[k].x,
            particles[i].y - particles[k].y
          );
          const dist3 = Math.hypot(
            particles[j].x - particles[k].x,
            particles[j].y - particles[k].y
          );
          if (dist < 100 && dist2 < 100 && dist3 < 100) {
            ctx.strokeStyle = "rgba(0, 0, 0, 0.2)";
            ctx.beginPath();
            ctx.moveTo(particles[i].x, particles[i].y);
            ctx.lineTo(particles[j].x, particles[j].y);
            ctx.lineTo(particles[k].x, particles[k].y);
            ctx.closePath();
            ctx.stroke();
          }
        }
      }
    }

    updateParticles();
    requestAnimationFrame(draw);
  };

  const updateParticles = () => {
    particles.forEach((p) => {
      p.x += p.vx;
      p.y += p.vy;

      // 边界反弹
      if (p.x < 0 || p.x > canvas.value.width) p.vx *= -1;
      if (p.y < 0 || p.y > canvas.value.height) p.vy *= -1;
    });
  };

  draw();

  // 处理窗口大小变化
  window.addEventListener("resize", () => {
    canvas.value.width = window.innerWidth;
    canvas.value.height = window.innerHeight;
  });
});
</script>

<style scoped>
.particles {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  background-color: #f0f0f0; /* 可以根据需要调整背景颜色 */
}
</style>