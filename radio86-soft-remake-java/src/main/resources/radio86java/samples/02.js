cls();

cur(3, 3); print("Прямокутник");

for(x = 0; x < 128; x+=1) {
  plot(x, 0, 1);
  plot(x, 49, 1);
}

for(y = 0; y < 50; y+=1) {
  plot(0, y, 1);
  plot(127, y, 1);
}
