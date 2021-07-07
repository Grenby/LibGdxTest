in vec2 v_pos;

const int N = 256;

uniform int u_octaves;
uniform int[512] u_transitions;
uniform float u_cellSize;
uniform vec2[256] u_directions;

float qunticCurve(float t){
    return t * t * t * (t * (t * 6 - 15) + 10);
}

int hash(int x0,int y0){
    return u_transitions[u_transitions[x0]+y0];
}

float getNoise(float x, float y){
    int x0 = int(mod(floor(x),N-1));
    int y0 = int(mod(floor(y),N-1));

    x-=floor(x);
    y-=floor(y);


    float dot1 = dot(u_directions[hash(x0,y0)],vec2(x,y));
    float dot2 = dot(u_directions[hash(x0+1,y0)],vec2(x-1,y));
    float dot3 = dot(u_directions[hash(x0,y0+1)],vec2(x,y-1));
    float dot4 = dot(u_directions[hash(x0+1,y0+1)],vec2(x-1,y-1));

    x = qunticCurve(x);
    y = qunticCurve(y);

    float ux = mix(dot3,dot4,x);
    float dx = mix(dot1,dot2,x);
    return (mix(dx,ux,y)+1)/2;;
}

float getNoise(float x, float y, int octaves){
    float amplitude = 1;
    float max = 0;
    float res = 0;
    while (octaves-1 > 0){
        octaves--;
        max += amplitude;
        res += getNoise(x,y) * amplitude;
        amplitude *= 0.5;
        x *= 2;
        y *= 2;
    }
    return res/max;
}

void main() {
    float c = getNoise(v_pos.x/u_cellSize,v_pos.y/u_cellSize,u_octaves);
    //c = u_transitions[1];
    gl_FragColor = vec4(c,c,c,1);
}
