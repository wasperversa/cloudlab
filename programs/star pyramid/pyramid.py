def print_pyramid(rows):
    for i in range(1, rows + 1):
        # Print spaces
        print(" " * (rows - i), end="")
        # Print stars
        print("*" * (2 * i - 1))

def main():
    numrows = int(input("Enter the number of rows for: "))
    print("Pyramid Pattern:")
    print_pyramid(numrows)

if __name__ == "__main__":
    main()
