import requests as r
from bs4 import BeautifulSoup as soup
import csv


def delete_bad_text(text):
    bad_string_list = ["Notes and references", "Notes", "See also"]
    bad_index = 0
    bad = True
    while bad and bad_index < 3:
        try:
            text = text[: text.index(bad_string_list[bad_index])]
        except ValueError:
            bad_index += 1
            continue

        bad = False

    return text


urls = ["https://en.wikipedia.org/wiki/Action_film#Science_fiction-action"]
visitedBefore = []
dictionaries = []

# crawl and scrape
index = 0
while len(urls) != 0 and index < 100:
    current_url = urls.pop(0)
    if current_url in visitedBefore:
        continue
    visitedBefore.append(current_url)
    print(index, current_url, sep="\t")

    # gets HTML from the url and parses it
    response = r.get(current_url)
    content = soup(response.content, "html.parser")

    # get links and content from wikipedia file
    body = content.find("div", {"class": "mw-content-ltr mw-parser-output"})
    text = delete_bad_text(soup.get_text(body)).replace("\n", "")

    dict = {}
    # dict["url"] = current_url
    dict["text"] = text
    dictionaries.append(dict)

    links = body.select("a[href]")

    # add more links
    for link in links:
        url = link["href"]

        hasDigit = False
        for i in url:
            if i in "0123456789" or i == ":":
                hasDigit = True

        if "/wiki/" in url and not hasDigit:
            urls.append("https://en.wikipedia.org" + url)

    index += 1


# write to csv
f = open("rnn/data.csv", "w")
writer = csv.writer(f)
for dict in dictionaries:
    writer.writerow(dict.values())
