#include <iostream>
using namespace std;

void fibonacci(int n) {
    int a = 0, b = 1;
    cout << "Fibonacci Sequence:" << endl;
    for (int i = 0; i < n; i++) {
        cout << a << " ";
        int next = a + b;
        a = b;
        b = next;
    }
    cout << endl;
}

int main() {
    int num;
    cout << "Enter the number of Fibonacci terms: ";
    cin >> num;
    fibonacci(num);
    return 0;
}
