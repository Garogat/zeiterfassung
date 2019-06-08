package zeiterfassung.reports;

import htmlProducer.HtmlElement;

/**
 * Base interface for cascading report objects
 */
public interface Reportable {
    HtmlElement getHtmlNode();
}
