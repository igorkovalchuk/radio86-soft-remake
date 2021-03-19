/*
 * Simple animation: circles (looks like fireworks)
 * Animation looks better/smoother when the "colored charset" enabled.
 */

const start = Date.now();
var millis;
var iteration = 0;
var fps = 0;

cls()

attempts = 20;

for (j = 1; j <= attempts; j++) {

    x0 = Math.random() * 150 + 5;
    y0 = Math.random() * 50 + 5;
    rMax = Math.random() * 30 + 20;

    for (i = 1; i < rMax; i++) {
        freeze();
        cls();
        circle1(x0, y0, i);
        unfreeze();
        iteration++;
        cur(0, 0);
        millis = Date.now() - start;
        fps = Math.floor(iteration / (millis / 1000));
        print("FPS " + fps + " ");
        pause(0.01);
    }

}

cls();

function circle1(x0, y0, r0) {
    var angle = 3.14159 * 2;
    var delta = 0.01;
    var x, y, r;
    for (a = 0; a < angle; a += delta) {
        r = r0 * (0 + Math.random());
        //r = r0;
        x = x0 + Math.cos(a) * r;
        y = y0 + Math.sin(a) * r;
        plot(x, y, 1);
    }
}
