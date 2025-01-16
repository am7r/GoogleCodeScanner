import re

def filter_lines(input_file, output_file):
    """
    Filter lines from the input file based on specific conditions and save the results to the output file.

    Conditions:
    - Remove lines containing "profile picture".
    - Remove lines with spaces.
    - Remove lines with less than 3 characters.
    - Remove lines containing invalid characters (anything other than lowercase letters, numbers, periods, or underscores).
    - Remove lines with capital letters.
    - Remove lines that exist entirely in the previous line.
    """
    username_pattern = re.compile(r"^[a-z0-9._]+$")

    with open(input_file, "r") as infile, open(output_file, "w") as outfile:
        previous_line = ""
        for line in infile:
            stripped_line = line.strip()
            # Skip lines containing "profile picture", lines with spaces, or lines with less than 3 characters
            if "profile picture" in stripped_line or ' ' in stripped_line:
                continue
            # Skip lines with invalid characters or capital letters
            if not username_pattern.match(stripped_line):
                continue
            # Skip the line if it exists entirely in the previous line
            if stripped_line in previous_line:
                continue
            outfile.write(stripped_line + '\n')
            previous_line = stripped_line

    print(f"Filtered lines saved to {output_file}.")


def check_filtered_accuracy(input_file, filtered_file):
    """
    Check the accuracy of the filtered file by ensuring that any line in the original file containing "profile picture"
    has its first 3 characters present within a range of lines (line number +/- 5) in the filtered file.
    """
    with open(filtered_file, "r") as filtered:
        filtered_lines = [line.strip() for line in filtered]

    counter = 0
    with open(input_file, "r") as infile:
        for line in infile:
            stripped_line = line.strip()
            if "profile picture" in stripped_line:
                counter += 1
                found = False
                # Check in the range of counter +/- 5 in the filtered file
                start_index = max(0, counter - 35)
                end_index = min(len(filtered_lines), counter + 35)
                for i in range(start_index, end_index):
                    if stripped_line[:3] == filtered_lines[i][:3]:
                        found = True
                        break
                if not found:
                    print(counter)
                    print(f"Mismatch found: {stripped_line}")

    print("Accuracy check complete.")

def find_unmatched_lines(following_file, followers_file):
    """
    Loop through filtered_following.txt and check if each line exists in filtered_followers.txt.
    Print lines from filtered_following.txt that do not exist in filtered_followers.txt.
    """
    with open(followers_file, "r") as followers:
        followers_lines = set(line.strip() for line in followers)

    with open(following_file, "r") as following:
        for line in following:
            stripped_line = line.strip()
            if stripped_line not in followers_lines:
                print(f"Unmatched line: {stripped_line}")

# Example usage
filter_lines("followers.txt", "filtered_followers.txt")
filter_lines("following.txt", "filtered_following.txt")
find_unmatched_lines("filtered_following.txt", "filtered_followers.txt")


