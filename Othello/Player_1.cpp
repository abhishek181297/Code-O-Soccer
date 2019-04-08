#include <bits/stdc++.h>


using namespace std;

const int MAX = 8;

set<pair <int, int> > findPlaceableLocations(char board[MAX][MAX], char player, char opponent) {
        set<pair <int, int> > placeablePositions;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == opponent) {
                    int I = i, J = j;
                    if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == '_') {
                        i = i + 1;
                        j = j + 1;
                        while (i < 7 && j < 7 && board[i][j] == opponent) {
                            i++;
                            j++;
                        }
                        if (i <= 7 && j <= 7 && board[i][j] == player) placeablePositions.insert(make_pair(I - 1, J - 1));
                    }
                    i = I;
                    j = J;
                    if (i - 1 >= 0 && board[i - 1][j] == '_') {
                        i = i + 1;
                        while (i < 7 && board[i][j] == opponent) i++;
                        if (i <= 7 && board[i][j] == player) placeablePositions.insert(make_pair(I - 1, J));
                    }
                    i = I;
                    if (i - 1 >= 0 && j + 1 <= 7 && board[i - 1][j + 1] == '_') {
                        i = i + 1;
                        j = j - 1;
                        while (i < 7 && j > 0 && board[i][j] == opponent) {
                            i++;
                            j--;
                        }
                        if (i <= 7 && j >= 0 && board[i][j] == player) placeablePositions.insert(make_pair(I - 1, J + 1));
                    }
                    i = I;
                    j = J;
                    if (j - 1 >= 0 && board[i][j - 1] == '_') {
                        j = j + 1;
                        while (j < 7 && board[i][j] == opponent) j++;
                        if (j <= 7 && board[i][j] == player) placeablePositions.insert(make_pair(I, J - 1));
                    }
                    j = J;
                    if (j + 1 <= 7 && board[i][j + 1] == '_') {
                        j = j - 1;
                        while (j > 0 && board[i][j] == opponent) j--;
                        if (j >= 0 && board[i][j] == player) placeablePositions.insert(make_pair(I, J + 1));
                    }
                    j = J;
                    if (i + 1 <= 7 && j - 1 >= 0 && board[i + 1][j - 1] == '_') {
                        i = i - 1;
                        j = j + 1;
                        while (i > 0 && j < 7 && board[i][j] == opponent) {
                            i--;
                            j++;
                        }
                        if (i >= 0 && j <= 7 && board[i][j] == player) placeablePositions.insert(make_pair(I + 1, J - 1));
                    }
                    i = I;
                    j = J;
                    if (i + 1 <= 7 && board[i + 1][j] == '_') {
                        i = i - 1;
                        while (i > 0 && board[i][j] == opponent) i--;
                        if (i >= 0 && board[i][j] == player) placeablePositions.insert(make_pair(I + 1, J));
                    }
                    i = I;
                    if (i + 1 <= 7 && j + 1 <= 7 && board[i + 1][j + 1] == '_') {
                        i = i - 1;
                        j = j - 1;
                        while (i > 0 && j > 0 && board[i][j] == opponent) {
                            i--;
                            j--;
                        }
                        if (i >= 0 && j >= 0 && board[i][j] == player) placeablePositions.insert(make_pair(I + 1, J + 1));
                    }
                    i = I;
                    j = J;
                }
            }
        }
        return placeablePositions;
    }


int main(int arg, char **args) {

    int n, m;

    char player;

    cin >> n >> m >> player;

    char a[MAX][MAX];

    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            cin >> a[i][j];

    cout << "Player 1: " << player << "\n";

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            cout << a[i][j] << " ";
	    cout << endl;
    }

    char opponent = player == 'B' ? 'W' : 'B';

    set <pair <int, int> > possibleMove = findPlaceableLocations(a, player, opponent);

    srand (time(NULL));

    int x, y;

    x = -1;
    y = -1;

    while (possibleMove.find(make_pair(x, y)) == possibleMove.end()) {
        x = rand() % n;
        y = rand() % m;
    }
    char code[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    cout << code[y] << x+1 << endl;

    return 0;
}