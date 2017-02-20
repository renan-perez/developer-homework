import { DiscussionsCliPage } from './app.po';

describe('discussions-cli App', function() {
  let page: DiscussionsCliPage;

  beforeEach(() => {
    page = new DiscussionsCliPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
