/*
 * Simple text effects
 * 
 * Keys:
 * up, down, left, right
 * 1 - recode "forward", 2 - recode "back", 3 - mix, 4 - down
 */

cls();
cur(0, 24);

// Please use your own location/file here
//var location = "/home/lampshade1/Android/Sdk/sources/android-29/android/graphics";
//var fileName = "Color.java";
//var f = localFile(location, fileName);

var path = "https://raw.githubusercontent.com/openjdk/jdk/jdk8-b120/jdk"
        + "/src/share/sample/scripting/scriptpad"
        + "/src/com/sun/sample/scriptpad/Main.java";
var f = remoteFile(path);

var sourceData = f.asArray();

if (sourceData.length === 0) {
    println("Can't find this file: " + location + '/' + fileName)
    stop();
}

var data = new Array();
for (var str of sourceData) {
    data.push(str.replace(/	/g, "    "));
}

var start = 0;

var lines = 23;

var line = "";

refresh = true;

while (true) {

    if (refresh) {

        cls();
        cur(0, 24);

        j = 0;
        k = 0;

        do {
            line = data[start + k];
            if (line === undefined) {
                line = "";
            }
            println(line);
            j++;
            if (line.length > 65) {
                j++;
            }
            if (line > 130) {
                j++;
            }
            k++;
        } while (j < lines)

    }

    refresh = true;

    k = inkey(0);

    if (k === "esc") {
        println("EXIT");
        break;
    }

    if (k === "up") {
        start--;
    }
    if (k === "down") {
        start++;
    }
    if (k === "right" || k === " ") {
        start += lines;
    }
    if (k === "left") {
        start -= lines;
    }

    if (k === "1") {
        recode(1);
        refresh = false;
    }
    if (k === "2") {
        recode(-1);
        refresh = false;
    }
    if (k === "3") {
        mix();
        refresh = false;
    }
    if (k === "4") {
        down();
        refresh = false;
    }
}

function recode(value) {
    for (x = 0; x < 65; x++) {
        for (y = 24; y >= 0; y--) {
            c = screen(x, y);
            n = asc(c)
            if ((n + value) >= 0 && (n + value) <= 255) {
                n = n + value;
            }
            c = chr(n);
            cur(x, y);
            print(c);
        }
    }
}

function mix() {
    var x2, y2, c2;
    for (x = 0; x < 65; x += 3) {
        for (y = 24; y >= 0; y -= 3) {
            c = screen(x, y);
            x2 = x + (Math.random() - 0.5) * 1.1;
            y2 = y + (Math.random() - 0.5) * 1.1;
            c2 = screen(x2, y2);
            cur(x, y);
            print(c2);
            cur(x2, y2);
            print(c);
        }
    }
}


function down() {
    var x2, y2, c2;
    for (x = 0; x < 65; x += 1) {
        for (y = 0; y <= 24; y += 1) {
            c = screen(x, y);
            x2 = x;
            y2 = y + 1;
            c2 = screen(x2, y2);
            if (c === " " || asc(c) === 0) {
                cur(x2, y2);
                print(c);
                cur(x, y);
                print(c2);
            }
        }
    }
}
