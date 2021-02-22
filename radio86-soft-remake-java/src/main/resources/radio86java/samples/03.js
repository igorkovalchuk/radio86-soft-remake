cls();

for(radius = 2; radius < 22; radius += 4) {
  for(i=0; i <= 359; i+=3) {
    a = i * 3.14159 / 180;
    dx = Math.cos(a) * radius;
    dy = Math.sin(a) * radius;
    plot(50+dx, 30+dy, 1);
  }
}
