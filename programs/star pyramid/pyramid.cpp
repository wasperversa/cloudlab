#include <iostream>
using namespace std;

void printPyramid(int rows) {
    for (int i = 1; i <= rows; i++) {
        // Print spaces
        for (int j = 1; j <= rows - i; j++) {
            cout << " ";
        }
        // Print stars
        for (int j = 1; j <= 2 * i - 1; j++) {
            cout << "*";
        }
        cout << endl;
    }
}

int main() {
    int numrows;
    cout << "Enter the number of rows for: ";
    cin >> numrows;
    cout << "Pyramid Pattern:" << endl;
    printPyramid(numrows);
    return 0;
}
