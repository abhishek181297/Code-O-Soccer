#include <bits/stdc++.h>

using namespace std;

int main(int arg, char **args) {

    int n, m;

    cin >> n >> m;

    int a[n][m];

    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            cin >> a[i][j];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            cout << a[i][j] << " ";
	cout << endl;
    }

    srand (time(NULL));

    switch (rand()%4) {
        case 0:
            cout << "up\n";
            break;
        case 1:
            cout << "down\n";
            break;
        case 2:
            cout << "left\n";
            break;
        case 3:
            cout << "right\n";
            break;
        default:
            break;
    }

    return 0;
}