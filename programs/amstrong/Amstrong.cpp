#include <iostream>
#include <cmath>
using namespace std;

bool isArmstrong(int num) {
    int originalNum = num, result = 0, digits = 0, temp = num;

    while (temp != 0) {
        digits++;
        temp /= 10;
    }

    temp = num;
    while (temp != 0) {
        int digit = temp % 10;
        result += pow(digit, digits);
        temp /= 10;
    }

    return result == originalNum;
}

int main() {
    int num;
    cout << "Enter a number: ";
    cin >> num;

    if (isArmstrong(num))
        cout << num << " is an Armstrong number." << endl;
    else
        cout << num << " is not an Armstrong number." << endl;

    return 0;
}
